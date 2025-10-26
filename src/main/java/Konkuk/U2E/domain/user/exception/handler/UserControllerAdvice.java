package Konkuk.U2E.domain.user.exception.handler;

import Konkuk.U2E.domain.user.exception.DuplicateUserException;
import Konkuk.U2E.domain.user.exception.InvalidAccessTokenException;
import Konkuk.U2E.global.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RestControllerAdvice(basePackages = "Konkuk.U2E.domain.user")
public class UserControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateUserException.class)
    public BaseErrorResponse handleDuplicateUser(DuplicateUserException e) {
        log.error("[handle_DuplicateUserException]", e);
        return new BaseErrorResponse(DUPLICATE_USER, e.getMessage());
    }

    // 유효하지 않은 액세스 토큰 예외 처리
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidAccessTokenException.class)
    public BaseErrorResponse handleInvalidAccessTokenException(InvalidAccessTokenException e) {
        log.error("[handleInvalidAccessTokenException]", e);
        return new BaseErrorResponse(INVALID_ACCESS_TOKEN, e.getMessage());
    }
}