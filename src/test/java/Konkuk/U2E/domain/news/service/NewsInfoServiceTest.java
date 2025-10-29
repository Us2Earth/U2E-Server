package Konkuk.U2E.domain.news.service;

import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.dto.response.GetNewsInfoResponse;
import Konkuk.U2E.domain.news.exception.NewsNotFoundException;
import Konkuk.U2E.domain.news.repository.NewsRepository;
import Konkuk.U2E.domain.news.service.mapper.NewsMapperFactory;
import Konkuk.U2E.domain.news.service.mapper.NewsMappingResult;
import Konkuk.U2E.global.openApi.gemini.service.NewsAiService;
import Konkuk.U2E.global.openApi.gemini.service.NewsRegionUpsertService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class NewsInfoServiceTest {

    NewsRepository newsRepository = Mockito.mock(NewsRepository.class);
    NewsMapperFactory newsMapperFactory = Mockito.mock(NewsMapperFactory.class);
    NewsAiService newsAiService = Mockito.mock(NewsAiService.class);
    NewsRegionUpsertService newsRegionUpsertService = Mockito.mock(NewsRegionUpsertService.class);

    NewsInfoService service = new NewsInfoService(
            newsRepository,
            newsMapperFactory,
            newsAiService,
            newsRegionUpsertService
    );

    @Test
    @DisplayName("newsId로 조회 성공 시, 엔티티와 매핑 결과가 GetNewsInfoResponse 로 반환된다")
    void getNewsInfo_success() {
        // given: 엔티티 더미
        News news = News.builder()
                .newsUrl("https://example.com/news")
                .imageUrl("https://example.com/img.jpg")
                .newsTitle("제목")
                .newsBody("본문 원문(길게)")
                .newsDate(LocalDate.of(2025,10,29))
                .climateList(List.of())
                .build();

        // AI 필드 세팅
        news.applyAiResult(
                "AI 솔루션",
                "관련1", "https://a.com",
                "관련2", "https://b.com",
                "관련3", "https://c.com"
        );
        news.applyAiSummary("요약 본문");

        when(newsRepository.findById(anyLong())).thenReturn(Optional.of(news));

        // 매퍼 스텁: climate/region 을 세팅한 NewsMappingResult 반환
        when(newsMapperFactory.newsMappingFunction())
                .thenReturn((News n) -> new NewsMappingResult(
                        List.of(),                 // climateProblems
                        List.of("Seoul","Busan"),  // regionNames
                        n
                ));

        // when
        GetNewsInfoResponse resp = service.getNewsInfo(1L);

        // then
        assertThat(resp).isNotNull();
        assertThat(resp.newsTitle()).isEqualTo("제목");
        assertThat(resp.newsUrl()).isEqualTo("https://example.com/news");
        assertThat(resp.newsImageUrl()).isEqualTo("https://example.com/img.jpg");
        // 주의: newsBody 는 entity.aiSummary 로 매핑됨
        assertThat(resp.newsBody()).isEqualTo("요약 본문");
        assertThat(resp.newsDate()).isEqualTo("2025-10-29");
        assertThat(resp.regionList()).containsExactly("Seoul","Busan");
        assertThat(resp.aiSolution()).isEqualTo("AI 솔루션");
        assertThat(resp.aiRelated()).hasSize(3);
        assertThat(StringUtils.hasText(resp.aiRelated().get(0).title())).isTrue();
        assertThat(StringUtils.hasText(resp.aiRelated().get(0).url())).isTrue();
    }

    @Test
    @DisplayName("newsId 조회 실패 시 NewsNotFoundException 발생")
    void getNewsInfo_notFound() {
        // given
        when(newsRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> service.getNewsInfo(999L))
                .isInstanceOf(NewsNotFoundException.class);
    }
}