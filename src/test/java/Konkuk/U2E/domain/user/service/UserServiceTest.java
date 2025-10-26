package Konkuk.U2E.domain.user.service;

import Konkuk.U2E.domain.user.domain.User;
import Konkuk.U2E.domain.user.dto.request.PostUserLoginRequest;
import Konkuk.U2E.domain.user.dto.response.PostUserLoginResponse;
import Konkuk.U2E.domain.user.exception.DuplicateUserException;
import Konkuk.U2E.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.DUPLICATE_USER;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    @DisplayName("회원 미존재 시 생성 후 토큰 발급")
    void signupAndLogin_createsAndIssuesToken() {
        // given
        UserRepository repo = mock(UserRepository.class);
        JwtUtil jwt = mock(JwtUtil.class);
        UserService sut = new UserService(repo, jwt);

        PostUserLoginRequest req = new PostUserLoginRequest("alice", "password123");

        when(repo.findUserByName("alice")).thenReturn(null);
        User saved = User.builder()
                .userId(1L)
                .name("alice")
                .password("password123")
                .build();
        when(repo.save(any(User.class))).thenReturn(saved);
        when(jwt.generateAccessToken("alice")).thenReturn("mock-jwt-token");

        // when
        PostUserLoginResponse res = sut.signupAndLogin(req);

        // then
        assertThat(res).isNotNull();
        assertThat(res.userId()).isEqualTo(1L);
        assertThat(res.token()).isEqualTo("mock-jwt-token");

        verify(repo).findUserByName("alice");
        verify(repo).save(any(User.class));
        verify(jwt).generateAccessToken("alice");
    }

    @Test
    @DisplayName("중복 사용자 존재 시 DuplicateUserException 발생")
    void signupAndLogin_duplicateUser_throws() {
        // given
        UserRepository repo = mock(UserRepository.class);
        JwtUtil jwt = mock(JwtUtil.class);
        UserService sut = new UserService(repo, jwt);

        PostUserLoginRequest req = new PostUserLoginRequest("alice", "pw");

        when(repo.findUserByName("alice"))
                .thenReturn(User.builder().userId(99L).name("alice").password("pw").build());

        // when & then
        assertThatThrownBy(() -> sut.signupAndLogin(req))
                .isInstanceOf(DuplicateUserException.class)
                .hasFieldOrPropertyWithValue("status", DUPLICATE_USER);

        verify(repo).findUserByName("alice");
        verify(repo, never()).save(any(User.class));
        verifyNoInteractions(jwt);
    }
}