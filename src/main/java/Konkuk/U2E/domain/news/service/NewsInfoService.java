package Konkuk.U2E.domain.news.service;

import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.dto.response.GetNewsInfoResponse;
import Konkuk.U2E.domain.news.exception.NewsNotFoundException;
import Konkuk.U2E.domain.news.repository.NewsRepository;
import Konkuk.U2E.domain.news.service.mapper.NewsMapperFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.NEWS_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class NewsInfoService {

    private final NewsRepository newsRepository;
    private final NewsMapperFactory newsMapperFactory;

    // 뉴스 상세보기
    public GetNewsInfoResponse getNewsInfo(Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFoundException(NEWS_NOT_FOUND));

        return GetNewsInfoResponse.of(newsMapperFactory.newsMappingFunction().apply(news));
    }
}
