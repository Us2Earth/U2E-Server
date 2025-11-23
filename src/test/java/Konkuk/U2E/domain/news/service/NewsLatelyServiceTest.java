package Konkuk.U2E.domain.news.service;

import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.dto.response.GetLatelyNewsResponse;
import Konkuk.U2E.domain.news.repository.NewsRepository;
import Konkuk.U2E.domain.news.service.mapper.NewsMapperFactory;
import Konkuk.U2E.domain.news.service.mapper.NewsMappingResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class NewsLatelyServiceTest {

    NewsRepository newsRepository = Mockito.mock(NewsRepository.class);
    NewsMapperFactory newsMapperFactory = Mockito.mock(NewsMapperFactory.class);
    NewsLatelyService service = new NewsLatelyService(newsRepository, newsMapperFactory);

    @Test
    @DisplayName("최신 5건 조회 후 매핑하여 GetLatelyNewsResponse 로 반환한다")
    void getLatelyNews_success() {
        // given: 더미 엔티티
        News n1 = News.builder()
                .newsUrl("u1").imageUrl("i1").newsTitle("t1")
                .newsBody("b1").newsDate(LocalDate.of(2025,10,28))
                .climateList(List.of())
                .build();

        News n2 = News.builder()
                .newsUrl("u2").imageUrl("i2").newsTitle("t2")
                .newsBody("b2").newsDate(LocalDate.of(2025,10,27))
                .climateList(List.of())
                .build();

        // repository 스텁
        when(newsRepository.findTop5ByOrderByNewsDateDesc()).thenReturn(List.of(n1, n2));

        // mapper 스텁: factory.newsMappingFunction() 호출 시 Function<News, NewsMappingResult> 반환
        when(newsMapperFactory.newsMappingFunction()).thenReturn(news -> {
            // 예시: regionNames 는 고정, climateProblems 비워둠
            return new NewsMappingResult(List.of(), List.of("Seoul"), news);
        });

        // when
        GetLatelyNewsResponse resp = service.getLatelyNews();

        // then
        assertThat(resp).isNotNull();
        assertThat(resp.latelyNewsList()).hasSize(2);
        assertThat(resp.latelyNewsList().get(0).newsTitle()).isEqualTo("t1");
        assertThat(resp.latelyNewsList().get(0).regionList()).containsExactly("Seoul");
    }
}