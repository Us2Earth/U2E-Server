package Konkuk.U2E.domain.news.exception.handler;

import Konkuk.U2E.domain.news.exception.NewsNotFoundException;
import Konkuk.U2E.global.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.CLIMATE_NOT_FOUND;
import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.NEWS_NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RestControllerAdvice(basePackages = "Konkuk.U2E.domain.news")
public class NewsControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NewsNotFoundException.class)
    public BaseErrorResponse handle_NewsNotFoundException(NewsNotFoundException e) {
        log.error("[handle_NewsNotFoundException]", e);
        return new BaseErrorResponse(NEWS_NOT_FOUND, e.getMessage());
    }
}
