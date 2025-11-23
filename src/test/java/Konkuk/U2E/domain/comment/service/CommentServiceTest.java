package Konkuk.U2E.domain.comment.service;

import Konkuk.U2E.domain.comment.domain.Comment;
import Konkuk.U2E.domain.comment.dto.request.PostCommentCreateRequest;
import Konkuk.U2E.domain.comment.dto.response.GetCommentsResponse;
import Konkuk.U2E.domain.comment.exception.CommentOfNewsNotFoundException;
import Konkuk.U2E.domain.comment.repository.CommentRepository;
import Konkuk.U2E.domain.news.domain.News;
import Konkuk.U2E.domain.news.repository.NewsRepository;
import Konkuk.U2E.domain.user.domain.User;
import Konkuk.U2E.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("CommentService 단위 테스트")
class CommentServiceTest {

    CommentRepository commentRepository = mock(CommentRepository.class);
    NewsRepository newsRepository = mock(NewsRepository.class);
    UserRepository userRepository = mock(UserRepository.class);

    CommentService sut;

    @BeforeEach
    void setUp() {
        sut = new CommentService(commentRepository, newsRepository, userRepository);
    }

    private News sampleNews() {
        return News.builder()
                .newsUrl("https://x")
                .imageUrl(null)
                .newsTitle("title")
                .newsBody("body")
                .newsDate(LocalDate.now())
                .climateList(List.of())
                .build();
    }

    @Test
    @DisplayName("getCommentInfo - 뉴스가 없으면 예외")
    void getCommentInfo_whenNewsMissing_thenThrow() {
        when(newsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.getCommentInfo(1L))
                .isInstanceOf(CommentOfNewsNotFoundException.class);
    }

    @Test
    @DisplayName("getCommentInfo - 정상 조회")
    void getCommentInfo_whenExists_thenOk() {
        News news = sampleNews();
        when(newsRepository.findById(1L)).thenReturn(Optional.of(news));

        Comment c = Comment.builder()
                .contents("hello")
                .news(news)
                .user(User.builder().name("kim").password("p").build())
                .build();

        when(commentRepository.findCommentsByNewsId(1L)).thenReturn(List.of(c));

        GetCommentsResponse res = sut.getCommentInfo(1L);

        assertThat(res.commentList()).hasSize(1);
        assertThat(res.commentList().get(0).contents()).isEqualTo("hello");
    }

    @Test
    @DisplayName("createComment - 뉴스가 없으면 예외")
    void createComment_whenNewsMissing_thenThrow() {
        when(newsRepository.findById(9L)).thenReturn(Optional.empty());
        when(userRepository.findUserByName("kim"))
                .thenReturn(User.builder().name("kim").password("p").build());

        PostCommentCreateRequest req = new PostCommentCreateRequest(9L, "hi");

        assertThatThrownBy(() -> sut.createComment("kim", req))
                .isInstanceOf(CommentOfNewsNotFoundException.class);
    }

    @Test
    @DisplayName("createComment - 정상 저장")
    void createComment_whenValid_thenSave() {
        User user = User.builder().name("kim").password("p").build();
        News news = sampleNews();
        when(userRepository.findUserByName("kim")).thenReturn(user);
        when(newsRepository.findById(7L)).thenReturn(Optional.of(news));

        PostCommentCreateRequest req = new PostCommentCreateRequest(7L, "hi");
        sut.createComment("kim", req);

        verify(commentRepository, times(1)).save(any(Comment.class));
    }
}
