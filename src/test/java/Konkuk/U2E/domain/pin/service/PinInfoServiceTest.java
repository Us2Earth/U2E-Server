package Konkuk.U2E.domain.pin.service;

import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.repository.ClimateRepository;
import Konkuk.U2E.domain.news.repository.NewsPinRepository;
import Konkuk.U2E.domain.pin.dto.response.GetPinInfoResponse;
import Konkuk.U2E.domain.pin.dto.response.NewsInfo;
import Konkuk.U2E.domain.pin.exception.PinNewsNotFoundException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest(classes = PinInfoService.class)
class PinInfoServiceTest {

    @MockitoBean NewsPinRepository newsPinRepository;
    @MockitoBean ClimateRepository climateRepository;

    @Resource
    PinInfoService service;

    @Test
    @DisplayName("핀에 연결된 뉴스가 있으면 DTO로 매핑하여 반환")
    void getPinInfo_success() {
        News n1 = News.builder().newsUrl("u1").imageUrl("i1").newsTitle("t1")
                .newsBody("b1").newsDate(LocalDate.of(2025,10,28)).climateList(List.of()).build();
        News n2 = News.builder().newsUrl("u2").imageUrl("i2").newsTitle("t2")
                .newsBody("b2").newsDate(LocalDate.of(2025,10,27)).climateList(List.of()).build();

        when(newsPinRepository.findNewsByPinId(anyLong())).thenReturn(List.of(n1, n2));
        when(climateRepository.findClimatesByNews(any())).thenReturn(List.of());

        GetPinInfoResponse resp = service.getPinInfo(99L);

        assertThat(resp.newsList()).hasSize(2);
        NewsInfo first = resp.newsList().get(0);
        assertThat(first.newsTitle()).isEqualTo("t1");
        assertThat(first.newsDate()).isEqualTo("2025-10-28");
    }

    @Test
    @DisplayName("뉴스 없으면 PinNewsNotFoundException")
    void getPinInfo_empty() {
        when(newsPinRepository.findNewsByPinId(anyLong())).thenReturn(List.of());
        assertThatThrownBy(() -> service.getPinInfo(1L))
                .isInstanceOf(PinNewsNotFoundException.class);
    }
}