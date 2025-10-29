package Konkuk.U2E.domain.pin.service;

import Konkuk.U2E.domain.news.domain.Climate;
import Konkuk.U2E.domain.news.domain.ClimateProblem;
import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.repository.ClimateRepository;
import Konkuk.U2E.domain.news.repository.NewsPinRepository;
import Konkuk.U2E.domain.pin.domain.Pin;
import Konkuk.U2E.domain.pin.domain.Region;
import Konkuk.U2E.domain.pin.dto.response.GetPinListResponse;
import Konkuk.U2E.domain.pin.dto.response.PinInfo;
import Konkuk.U2E.domain.pin.exception.InvalidParamException;
import Konkuk.U2E.domain.pin.exception.NewsPinNotFoundException;
import Konkuk.U2E.domain.pin.exception.PinNewsNotFoundException;
import Konkuk.U2E.domain.pin.repository.PinRepository;
import Konkuk.U2E.domain.pin.repository.RegionRepository;
import Konkuk.U2E.global.util.DateUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(classes = PinListService.class)
class PinListServiceTest {

    @MockitoBean ClimateRepository climateRepository;
    @MockitoBean NewsPinRepository newsPinRepository;
    @MockitoBean PinRepository pinRepository;
    @MockitoBean RegionRepository regionRepository;
    @MockitoBean DateUtil dateUtil;

    @Resource
    PinListService service;

    Region seoul;
    Region busan;
    Pin pin1;
    Pin pin2;

    @BeforeEach
    void setup() {
        seoul = Region.builder()
                .name("Seoul")
                .latitude(new BigDecimal("37.5665"))
                .longitude(new BigDecimal("126.9780"))
                .build();
        busan = Region.builder()
                .name("Busan")
                .latitude(new BigDecimal("35.1796"))
                .longitude(new BigDecimal("129.0756"))
                .build();

        // 테스트에서는 pinId(null) 그대로 사용
        pin1 = Pin.builder().region(seoul).build();
        pin2 = Pin.builder().region(busan).build();
    }

    @Test
    @DisplayName("전체 핀 – 모든 파라미터 null")
    void getPinList_all() {
        when(pinRepository.findAll()).thenReturn(List.of(pin1, pin2));

        News news1 = News.builder().newsUrl("u1").imageUrl("i1").newsTitle("t1")
                .newsBody("b1").newsDate(LocalDate.of(2025,10,20)).climateList(List.of()).build();
        News news2 = News.builder().newsUrl("u2").imageUrl("i2").newsTitle("t2")
                .newsBody("b2").newsDate(LocalDate.of(2025,10,18)).climateList(List.of()).build();

        // 중요: pinId가 null이므로 anyLong()이 아닌 any() 사용 (null 매칭)
        when(newsPinRepository.findNewsByPinId(any())).thenReturn(List.of(news1), List.of(news2));

        when(dateUtil.getLatestNews(anyList())).thenAnswer(inv -> ((List<News>) inv.getArgument(0)).get(0));
        when(dateUtil.checkLately(any())).thenReturn(true);
        when(climateRepository.findClimatesByNews(any())).thenReturn(List.of());

        GetPinListResponse resp = service.getPinList(null, null, null);

        assertThat(resp.pinList()).hasSize(2);
        assertThat(resp.pinList().get(0).region()).isEqualTo("Seoul");
        assertThat(resp.pinList().get(0).isLately()).isTrue();
    }

    @Test
    @DisplayName("지역 필터 – region=Seoul")
    void getPinList_region() {
        when(regionRepository.findRegionsByName(eq("Seoul"))).thenReturn(List.of(seoul));
        when(pinRepository.findPinByRegion(seoul)).thenReturn(pin1);

        News news = News.builder().newsUrl("u").imageUrl("i").newsTitle("t")
                .newsBody("b").newsDate(LocalDate.of(2025,10,10)).climateList(List.of()).build();

        when(newsPinRepository.findNewsByPinId(any())).thenReturn(List.of(news));
        when(dateUtil.getLatestNews(anyList())).thenReturn(news);
        when(dateUtil.checkLately(any())).thenReturn(false);
        when(climateRepository.findClimatesByNews(any())).thenReturn(List.of());

        GetPinListResponse resp = service.getPinList("Seoul", null, null);

        assertThat(resp.pinList()).hasSize(1);
        PinInfo info = resp.pinList().get(0);
        assertThat(info.region()).isEqualTo("Seoul");
        assertThat(info.isLately()).isFalse();
    }

    @Test
    @DisplayName("뉴스ID 필터 – newsId=10 (핀 없으면 예외)")
    void getPinList_newsId_empty() {
        when(newsPinRepository.findPinsByNewsId(eq(10L))).thenReturn(List.of());
        assertThatThrownBy(() -> service.getPinList(null, null, 10L))
                .isInstanceOf(NewsPinNotFoundException.class);
    }

    @Test
    @DisplayName("뉴스ID 필터 – 정상 반환")
    void getPinList_newsId_ok() {
        when(newsPinRepository.findPinsByNewsId(eq(10L))).thenReturn(List.of(pin1));

        News n = News.builder().newsUrl("u").imageUrl("i").newsTitle("t")
                .newsBody("b").newsDate(LocalDate.of(2025,10,1)).climateList(List.of()).build();

        // 중요: pinId가 null이라 any() 사용
        when(newsPinRepository.findNewsByPinId(any())).thenReturn(List.of(n));
        when(dateUtil.getLatestNews(anyList())).thenReturn(n);
        when(dateUtil.checkLately(any())).thenReturn(true);
        when(climateRepository.findClimatesByNews(any())).thenReturn(List.of());

        GetPinListResponse resp = service.getPinList(null, null, 10L);

        assertThat(resp.pinList()).hasSize(1);
        assertThat(resp.pinList().get(0).region()).isEqualTo("Seoul");
    }

    @Test
    @DisplayName("잘못된 파라미터 조합 – InvalidParamException")
    void getPinList_invalidParams() {
        assertThatThrownBy(() -> service.getPinList("Seoul", "TEMPERATURE_RISE", null))
                .isInstanceOf(InvalidParamException.class);
        assertThatThrownBy(() -> service.getPinList("Seoul", null, 1L))
                .isInstanceOf(InvalidParamException.class);
        assertThatThrownBy(() -> service.getPinList(null, "TEMPERATURE_RISE", 1L))
                .isInstanceOf(InvalidParamException.class);
    }

    @Test
    @DisplayName("뉴스 없는 핀 – createPinInfo 내 예외 흐름")
    void createPinInfo_noNews() {
        when(pinRepository.findAll()).thenReturn(List.of(pin1));
        // 중요: any()로 null 매칭
        when(newsPinRepository.findNewsByPinId(any())).thenReturn(List.of());

        assertThatThrownBy(() -> service.getPinList(null, null, null))
                .isInstanceOf(PinNewsNotFoundException.class);
    }
}