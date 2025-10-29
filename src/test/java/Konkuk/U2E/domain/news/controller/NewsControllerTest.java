package Konkuk.U2E.domain.news.controller;

import Konkuk.U2E.domain.news.dto.response.GetLatelyNewsResponse;
import Konkuk.U2E.domain.news.dto.response.GetNewsInfoResponse;
import Konkuk.U2E.domain.news.dto.response.LatelyNews;
import Konkuk.U2E.domain.news.service.NewsInfoService;
import Konkuk.U2E.domain.news.service.NewsLatelyService;
import Konkuk.U2E.global.openApi.gemini.dto.response.RelatedArticle;
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

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class NewsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    NewsLatelyService newsLatelyService;

    @MockitoBean
    NewsInfoService newsInfoService;

    @Nested
    @DisplayName("GET /news/lately")
    class LatelyApi {

        @Test
        @DisplayName("최신 뉴스 목록을 BaseResponse(result)로 감싸서 반환한다")
        void viewLatelyNewsList() throws Exception {
            // given
            LatelyNews n1 = new LatelyNews(
                    1L,
                    List.of("Seoul", "Busan"),  // regionList
                    List.of(),                  // climateList (enum 리스트, 여기선 빈 리스트)
                    "제목1"
            );
            LatelyNews n2 = new LatelyNews(
                    2L,
                    List.of("Incheon"),
                    List.of(),
                    "제목2"
            );
            GetLatelyNewsResponse response = GetLatelyNewsResponse.of(List.of(n1, n2));

            Mockito.when(newsLatelyService.getLatelyNews()).thenReturn(response);

            // when & then
            mockMvc.perform(get("/news/lately").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    // BaseResponse 가 {"result": {...}} 라는 전제
                    .andExpect(jsonPath("$.data.latelyNewsList", hasSize(2)))
                    .andExpect(jsonPath("$.data.latelyNewsList[0].newsId").value(1L))
                    .andExpect(jsonPath("$.data.latelyNewsList[0].newsTitle").value("제목1"))
                    .andExpect(jsonPath("$.data.latelyNewsList[0].regionList", hasSize(2)))
                    .andExpect(jsonPath("$.data.latelyNewsList[0].regionList[0]").value("Seoul"))
                    .andExpect(jsonPath("$.data.latelyNewsList[1].newsId").value(2L))
                    .andExpect(jsonPath("$.data.latelyNewsList[1].regionList[0]").value("Incheon"));
        }
    }

    @Nested
    @DisplayName("GET /news/{newsId}")
    class InfoApi {

        @Test
        @DisplayName("뉴스 상세를 BaseResponse(result)로 감싸서 반환한다")
        void viewNewsInfo() throws Exception {
            // given
            GetNewsInfoResponse dto = new GetNewsInfoResponse(
                    List.of(),                                   // climateList
                    List.of("Seoul", "Busan"),                   // regionList
                    "테스트 제목",                                 // newsTitle
                    "https://example.com/news",                  // newsUrl
                    "https://example.com/img.jpg",               // newsImageUrl
                    "요약 본문",                                  // newsBody (aiSummary 매핑)
                    "2025-10-29",                                // newsDate
                    "AI 솔루션 본문",                              // aiSolution
                    List.of(new RelatedArticle("관련1", "https://a.com"))
            );

            Mockito.when(newsInfoService.getNewsInfo(anyLong())).thenReturn(dto);

            // when & then
            mockMvc.perform(get("/news/{newsId}", 10L).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.data.newsTitle").value("테스트 제목"))
                    .andExpect(jsonPath("$.data.newsUrl").value("https://example.com/news"))
                    .andExpect(jsonPath("$.data.newsImageUrl").value("https://example.com/img.jpg"))
                    .andExpect(jsonPath("$.data.newsBody").value("요약 본문"))
                    .andExpect(jsonPath("$.data.newsDate").value("2025-10-29"))
                    .andExpect(jsonPath("$.data.regionList", hasSize(2)))
                    .andExpect(jsonPath("$.data.aiSolution").value("AI 솔루션 본문"))
                    .andExpect(jsonPath("$.data.aiRelated", hasSize(1)))
                    .andExpect(jsonPath("$.data.aiRelated[0].title").value("관련1"))
                    .andExpect(jsonPath("$.data.aiRelated[0].url").value("https://a.com"));
        }
    }
}