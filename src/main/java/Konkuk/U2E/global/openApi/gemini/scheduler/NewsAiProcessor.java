package Konkuk.U2E.global.openApi.gemini.scheduler;

import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.repository.NewsRepository;
import Konkuk.U2E.global.openApi.gemini.dto.request.AiNewsRequest;
import Konkuk.U2E.global.openApi.gemini.dto.response.AiResponse;
import Konkuk.U2E.global.openApi.gemini.service.NewsAiService;
import Konkuk.U2E.global.openApi.gemini.service.NewsClimateUpsertService;
import Konkuk.U2E.global.openApi.gemini.service.NewsRegionUpsertService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsAiProcessor {

    private final NewsRepository newsRepository;
    private final NewsAiService newsAiService;
    private final NewsRegionUpsertService newsRegionUpsertService;
    private final NewsClimateUpsertService newsClimateUpsertService;

    @Transactional
    public void processOneTransactional(Long newsId) {
        News news = newsRepository.findById(newsId).orElse(null);
        if (news == null) return;
        if (news.getAiSummary() != null && !news.getAiSummary().isBlank()) return;

        int attempts = 0;
        int maxAttempts = 3;
        long backoffMs = 1500L;

        while (true) {
            attempts++;
            try {
                AiResponse ai = newsAiService.analyzeAll(new AiNewsRequest(news.getNewsBody(), "en"));

                String t1 = relTitle(ai, 0); String u1 = relUrl(ai, 0);
                String t2 = relTitle(ai, 1); String u2 = relUrl(ai, 1);
                String t3 = relTitle(ai, 2); String u3 = relUrl(ai, 2);

                news.applyAiResult(ai.solution(), t1, u1, t2, u2, t3, u3);
                news.applyAiSummary(ai.summary());

                // 지역/핀 매핑
                newsRegionUpsertService.linkFromCandidates(news, ai.regions());

                // 기후문제 매핑 (DB 교체 저장)
                newsClimateUpsertService.replaceWithEnums(news, ai.climateProblems());

                return;

            } catch (Exception e) {
                if (attempts >= maxAttempts) {
                    log.error("[NewsAiFill] give up newsId={} after {} attempts: {}", newsId, attempts, e.toString());
                    return;
                }
                log.warn("[NewsAiFill] retry newsId={} attempt={}/{} : {}", newsId, attempts, maxAttempts, e.toString());
                sleep(backoffMs);
                backoffMs *= 2;
            }
        }
    }

    private String relTitle(AiResponse a, int i) {
        return (a.related() != null && a.related().size() > i) ? a.related().get(i).title() : null;
    }
    private String relUrl(AiResponse a, int i) {
        return (a.related() != null && a.related().size() > i) ? a.related().get(i).url() : null;
    }
    private void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException ignored) {} }
}