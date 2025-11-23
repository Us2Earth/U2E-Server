package Konkuk.U2E.domain.news.service.mapper;

import Konkuk.U2E.domain.news.domain.Climate;
import Konkuk.U2E.domain.news.domain.ClimateProblem;
import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.repository.ClimateRepository;
import Konkuk.U2E.domain.news.repository.NewsPinRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class NewsMapperFactoryTest {

    ClimateRepository climateRepository = Mockito.mock(ClimateRepository.class);
    NewsPinRepository newsPinRepository = Mockito.mock(NewsPinRepository.class);

    NewsMapperFactory factory = new NewsMapperFactory(climateRepository, newsPinRepository);

    @Test
    @DisplayName("news → (climateProblems, regionNames, news) 매핑 정상 동작")
    void mapping_success() {
        // given
        News news = News.builder()
                .newsUrl("https://example.com/news")
                .imageUrl("https://example.com/img.jpg")
                .newsTitle("제목")
                .newsBody("본문")
                .newsDate(LocalDate.of(2025, 10, 30))
                .climateList(List.of()) // 엔티티 내부용 리스트(무관)
                .build();

        // region 이름 조회는 newsId(null) 일 수 있으므로 anyLong() 대신 any() 사용
        when(newsPinRepository.findRegionNameByNews(any()))
                .thenReturn(List.of("Seoul", "Busan"));

        // 기후 문제 리스트는 Climate -> ClimateProblem 으로 매핑됨
        when(climateRepository.findClimatesByNews(any(News.class)))
                .thenReturn(List.of(
                        Climate.builder().climateProblem(ClimateProblem.TEMPERATURE_RISE).news(news).build(),
                        Climate.builder().climateProblem(ClimateProblem.HEAVY_RAIN_OR_FLOOD).news(news).build()
                ));

        Function<News, NewsMappingResult> fn = factory.newsMappingFunction();

        // when
        NewsMappingResult result = fn.apply(news);

        // then
        assertThat(result).isNotNull();
        assertThat(result.news()).isSameAs(news);

        assertThat(result.regionNames()).containsExactly("Seoul", "Busan");

        assertThat(result.climateProblems())
                .containsExactly(
                        ClimateProblem.TEMPERATURE_RISE,
                        ClimateProblem.HEAVY_RAIN_OR_FLOOD
                );
    }

    @Test
    @DisplayName("연관 데이터가 없으면 빈 리스트로 매핑된다")
    void mapping_emptyLists() {
        // given
        News news = News.builder()
                .newsUrl("u").imageUrl("i").newsTitle("t")
                .newsBody("b").newsDate(LocalDate.of(2025, 10, 29))
                .climateList(List.of())
                .build();

        when(newsPinRepository.findRegionNameByNews(any())).thenReturn(List.of());
        when(climateRepository.findClimatesByNews(any(News.class))).thenReturn(List.of());

        Function<News, NewsMappingResult> fn = factory.newsMappingFunction();

        // when
        NewsMappingResult result = fn.apply(news);

        // then
        assertThat(result.regionNames()).isEmpty();
        assertThat(result.climateProblems()).isEmpty();
        assertThat(result.news()).isSameAs(news);
    }
}