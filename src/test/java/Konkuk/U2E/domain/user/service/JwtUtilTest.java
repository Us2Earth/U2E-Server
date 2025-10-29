package Konkuk.U2E.domain.user.service;

import Konkuk.U2E.domain.user.exception.InvalidAccessTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.INVALID_ACCESS_TOKEN;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DisplayName("JwtUtil 단위 테스트")
class JwtUtilTest {

    private JwtUtil jwtUtil = new JwtUtil("abcdefghakjfnjklafljkaklfjaslkdfjlkjkljklafjkkljflkaflksjklijklmnopqrstuvwxyz");

    @Test
    @DisplayName("토큰 생성/검증 - OK (username 클레임 반환)")
    void generate_and_validate_ok() {
        // when
        String token = jwtUtil.generateAccessToken("tester");

        // then
        String name = jwtUtil.validateAndGetName(token);
        assertThat(name).isEqualTo("tester");
    }

    @Test
    @DisplayName("잘못된 토큰 - INVALID_ACCESS_TOKEN 예외")
    void validate_invalidToken_thenThrow() {
        assertThatThrownBy(() -> jwtUtil.validateAndGetName("not.jwt"))
                .isInstanceOf(InvalidAccessTokenException.class)
                .hasMessageContaining(INVALID_ACCESS_TOKEN.getMessage());
    }

    @Test
    @DisplayName("만료된 토큰 - INVALID_ACCESS_TOKEN 예외")
    void validate_expiredToken_thenThrow() {
        // 만료 시간을 과거로 세팅해서 즉시 만료 토큰 생성
        ReflectionTestUtils.setField(jwtUtil, "expirationMs", -1L);
        String token = jwtUtil.generateAccessToken("tester");

        assertThatThrownBy(() -> jwtUtil.validateAndGetName(token))
                .isInstanceOf(InvalidAccessTokenException.class)
                .hasMessageContaining(INVALID_ACCESS_TOKEN.getMessage());
    }
}
