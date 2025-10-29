package Konkuk.U2E.domain.news.service;

import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.dto.response.GetNewsInfoResponse;
import Konkuk.U2E.domain.news.exception.NewsNotFoundException;
import Konkuk.U2E.domain.news.repository.NewsRepository;
import Konkuk.U2E.domain.news.service.mapper.NewsMapperFactory;
import Konkuk.U2E.global.openApi.gemini.dto.request.AiNewsRequest;
import Konkuk.U2E.global.openApi.gemini.dto.response.AiResponse;
import Konkuk.U2E.global.openApi.gemini.dto.response.RelatedArticle;
import Konkuk.U2E.global.openApi.gemini.service.NewsAiService;
import Konkuk.U2E.global.openApi.gemini.service.NewsRegionUpsertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.NEWS_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class NewsInfoService {

    private final NewsRepository newsRepository;
    private final NewsMapperFactory newsMapperFactory;

    private final NewsAiService aggregatedNewsAiService;
    private final NewsRegionUpsertService newsRegionUpsertService;

    @Transactional
    public GetNewsInfoResponse getNewsInfo(Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFoundException(NEWS_NOT_FOUND));

        // 여기서는 DB에 있는 값만 사용 (Gemini 호출/저장 X)
        var mapping = newsMapperFactory.newsMappingFunction().apply(news);
        List<RelatedArticle> related = fromEntity(news);
        return GetNewsInfoResponse.of(mapping, news.getAiSolution(), related);
    }

    private List<RelatedArticle> fromEntity(News n) {
        List<RelatedArticle> list = new ArrayList<>(3);
        if (StringUtils.hasText(n.getAiRelated1Title()) || StringUtils.hasText(n.getAiRelated1Url()))
            list.add(new RelatedArticle(n.getAiRelated1Title(), n.getAiRelated1Url()));
        if (StringUtils.hasText(n.getAiRelated2Title()) || StringUtils.hasText(n.getAiRelated2Url()))
            list.add(new RelatedArticle(n.getAiRelated2Title(), n.getAiRelated2Url()));
        if (StringUtils.hasText(n.getAiRelated3Title()) || StringUtils.hasText(n.getAiRelated3Url()))
            list.add(new RelatedArticle(n.getAiRelated3Title(), n.getAiRelated3Url()));
        return list;
    }

}
