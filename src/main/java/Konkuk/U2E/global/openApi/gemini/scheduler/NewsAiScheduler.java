package Konkuk.U2E.global.openApi.gemini.scheduler;

import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsAiScheduler {

    private final NewsRepository newsRepository;
    private final NewsAiProcessor newsAiProcessor;

        @Scheduled(cron = "*/30 * * * * *", zone = "Asia/Seoul")    // 테스트용으로 30초마다 확인
//    @Scheduled(cron = "0 30 3 * * WED", zone = "Asia/Seoul")    // 매주 수요일 03:30
    public void fillMissingAiFieldsDaily() {
        final int PAGE_SIZE = 50;
        int page = 0;

        log.info("[NewsAiFill] start");

        while (true) {
            Page<News> slice = newsRepository.findByAiSummaryIsNull(PageRequest.of(page, PAGE_SIZE));
            if (slice.isEmpty()) break;

            slice.forEach(n -> processOneSafely(n.getNewsId()));

            if (!slice.hasNext()) break;
            page++;
        }

        log.info("[NewsAiFill] done");
    }

    private void processOneSafely(Long newsId) {
        try {
            newsAiProcessor.processOneTransactional(newsId);
            sleep(200L);
        } catch (Exception e) {
            log.error("[NewsAiFill] failed newsId={} : {}", newsId, e.toString());
        }
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
