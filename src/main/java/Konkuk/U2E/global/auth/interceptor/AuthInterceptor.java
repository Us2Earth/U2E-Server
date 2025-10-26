package Konkuk.U2E.global.auth.interceptor;

import Konkuk.U2E.domain.user.service.JwtUtil;
import Konkuk.U2E.domain.user.exception.InvalidAccessTokenException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.INVALID_ACCESS_TOKEN;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;
    private static final String GET_METHOD = "GET";
    private static final String VIEW_COMMENTS_PATH = "/comments";


    public AuthInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String method = request.getMethod();
        String path = request.getRequestURI();

        // 댓글 조회 시 인증 건너뛰기
        if (method.equals(GET_METHOD) && path.startsWith(VIEW_COMMENTS_PATH)) {
            return true;
        }

        String token = request.getHeader("Authorization");

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (token != null) {
            Claims claims = jwtUtil.validateAccessToken(token);
            request.setAttribute("username", claims.getSubject());
            log.info("사용자 {} 인증", request.getAttribute("username"));
            return true;
        }

        throw new InvalidAccessTokenException(INVALID_ACCESS_TOKEN);
    }
}
