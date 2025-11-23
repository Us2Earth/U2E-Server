package Konkuk.U2E.domain.pin.controller;

import Konkuk.U2E.domain.pin.dto.response.GetPinInfoResponse;
import Konkuk.U2E.domain.pin.dto.response.GetPinListResponse;
import Konkuk.U2E.domain.pin.dto.response.NewsInfo;
import Konkuk.U2E.domain.pin.dto.response.PinInfo;
import Konkuk.U2E.domain.news.domain.ClimateProblem;
import Konkuk.U2E.domain.pin.service.PinInfoService;
import Konkuk.U2E.domain.pin.service.PinListService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class PinControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    PinInfoService pinInfoService;

    @MockitoBean
    PinListService pinListService;

    @Nested
    @DisplayName("GET /pin/{pinId}")
    class PinInfoApi {

        @Test
        @DisplayName("핀 상세 – BaseResponse(data.newsList) 반환")
        void viewPinInfo() throws Exception {
            GetPinInfoResponse stub = GetPinInfoResponse.of(
                    List.of(
                            new NewsInfo(List.of(ClimateProblem.TEMPERATURE_RISE), 1L, "뉴스1", "본문1", "2025-10-29"),
                            new NewsInfo(List.of(ClimateProblem.HEAVY_RAIN_OR_FLOOD), 2L, "뉴스2", "본문2", "2025-10-28")
                    )
            );

            Mockito.when(pinInfoService.getPinInfo(anyLong())).thenReturn(stub);

            mockMvc.perform(get("/pin/{pinId}", 100L).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.data.newsList", hasSize(2)))
                    .andExpect(jsonPath("$.data.newsList[0].newsId").value(1L))
                    .andExpect(jsonPath("$.data.newsList[0].climateList[0]").value("TEMPERATURE_RISE"))
                    .andExpect(jsonPath("$.data.newsList[1].newsTitle").value("뉴스2"));
        }
    }

    @Nested
    @DisplayName("GET /pin (쿼리 파라미터 조합)")
    class PinListApi {

        @Test
        @DisplayName("전체 핀 목록 – 파라미터 없음")
        void viewPinList_all() throws Exception {
            GetPinListResponse stub = GetPinListResponse.of(
                    List.of(
                            new PinInfo(1L, new BigDecimal("37.5665"), new BigDecimal("126.9780"), true, "Seoul", List.of()),
                            new PinInfo(2L, new BigDecimal("35.1796"), new BigDecimal("129.0756"), false, "Busan", List.of(ClimateProblem.FINE_DUST))
                    )
            );

            Mockito.when(pinListService.getPinList(isNull(), isNull(), isNull())).thenReturn(stub);

            mockMvc.perform(get("/pin").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.pinList", hasSize(2)))
                    .andExpect(jsonPath("$.data.pinList[0].pinId").value(1L))
                    .andExpect(jsonPath("$.data.pinList[1].region").value("Busan"));
        }

        @Test
        @DisplayName("지역으로 필터 – region=Seoul")
        void viewPinList_region() throws Exception {
            GetPinListResponse stub = GetPinListResponse.of(
                    List.of(new PinInfo(3L, new BigDecimal("37.5"), new BigDecimal("127.0"), true, "Seoul", List.of()))
            );
            Mockito.when(pinListService.getPinList(eq("Seoul"), isNull(), isNull())).thenReturn(stub);

            mockMvc.perform(get("/pin").param("region", "Seoul").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.pinList", hasSize(1)))
                    .andExpect(jsonPath("$.data.pinList[0].region").value("Seoul"));
        }

        @Test
        @DisplayName("기후로 필터 – climate=TEMPERATURE_RISE")
        void viewPinList_climate() throws Exception {
            GetPinListResponse stub = GetPinListResponse.of(
                    List.of(new PinInfo(4L, new BigDecimal("33.0"), new BigDecimal("129.0"), false, "Jeju", List.of(ClimateProblem.TEMPERATURE_RISE)))
            );
            Mockito.when(pinListService.getPinList(isNull(), eq("TEMPERATURE_RISE"), isNull())).thenReturn(stub);

            mockMvc.perform(get("/pin").param("climate", "TEMPERATURE_RISE").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.pinList[0].climateProblem[0]").value("TEMPERATURE_RISE"));
        }

        @Test
        @DisplayName("뉴스 카드 핀 목록 – newsId=10")
        void viewPinList_newsId() throws Exception {
            GetPinListResponse stub = GetPinListResponse.of(
                    List.of(new PinInfo(5L, new BigDecimal("36.0"), new BigDecimal("128.0"), true, "Daegu", List.of()))
            );
            Mockito.when(pinListService.getPinList(isNull(), isNull(), eq(10L))).thenReturn(stub);

            mockMvc.perform(get("/pin").param("newsId", "10").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.pinList[0].pinId").value(5L))
                    .andExpect(jsonPath("$.data.pinList[0].region").value("Daegu"));
        }
    }
}