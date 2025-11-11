package Konkuk.U2E.domain.comment.controller;

import Konkuk.U2E.domain.comment.dto.request.PostCommentCreateRequest;
import Konkuk.U2E.domain.comment.dto.response.GetCommentsResponse;
import Konkuk.U2E.domain.comment.service.CommentService;
import Konkuk.U2E.domain.user.service.JwtUtil;
import Konkuk.U2E.global.auth.resolver.LoginUserArgumentResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper om;

    @MockitoBean CommentService commentService;

    @MockitoBean LoginUserArgumentResolver loginUserArgumentResolver;

    @BeforeEach
    void setUpResolver() throws Exception {
        when(loginUserArgumentResolver.supportsParameter(any())).thenReturn(true);
        when(loginUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .thenReturn("test-user");
    }

    @MockitoBean
    JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        when(jwtUtil.validateAndGetName("dummy-token")).thenReturn("test-user");
    }

    @Nested
    @DisplayName("GET /comments/{newsId}")
    class GetCommentsApi {

        @Test
        @DisplayName("댓글 목록 조회 – OK (BaseResponse.data.commentList)")
        void getComments_ok() throws Exception {
            // given
            when(commentService.getCommentInfo(1L))
                    .thenReturn(new GetCommentsResponse(List.of()));

            // when & then
            mockMvc.perform(get("/comments/{newsId}", 1L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.data.commentList").isArray());
        }
    }

    @Nested
    @DisplayName("POST /comments")
    class PostCommentsApi {

        @Test
        @DisplayName("댓글 작성 – OK (LoginUser가 'test-user'로 주입)")
        void postComments_ok() throws Exception {
            // given
            PostCommentCreateRequest req = new PostCommentCreateRequest(5L, "hi");

            // when & then
            mockMvc.perform(post("/comments")
                            .header("Authorization", "Bearer dummy-token")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(om.writeValueAsString(req)))
                    .andExpect(status().isOk());

            verify(commentService).createComment(eq("test-user"), any(PostCommentCreateRequest.class));
        }
    }
}