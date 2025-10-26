package Konkuk.U2E.domain.pin.exception.handler;

import Konkuk.U2E.domain.pin.exception.ClimateNotFoundException;
import Konkuk.U2E.domain.pin.exception.NewsPinNotFoundException;
import Konkuk.U2E.domain.pin.exception.InvalidParamException;
import Konkuk.U2E.domain.pin.exception.PinNewsNotFoundException;
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
@RestControllerAdvice(basePackages = "Konkuk.U2E.domain.pin")
public class PinControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PinNewsNotFoundException.class)
    public BaseErrorResponse handle_NewsPinNotFoundException(PinNewsNotFoundException e) {
        log.error("[handle_PinNewsNotFoundException]", e);
        return new BaseErrorResponse(PINNEWS_NOT_FOUND, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParamException.class)
    public BaseErrorResponse handle_InvalidParamException(InvalidParamException e) {
        log.error("[handle_InvalidParamException]", e);
        return new BaseErrorResponse(INVALID_PARAM, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NewsPinNotFoundException.class)
    public BaseErrorResponse handle_NewsNotFoundException(NewsPinNotFoundException e) {
        log.error("[handle_NewsPinNotFoundException]", e);
        return new BaseErrorResponse(NEWSPIN_NOT_FOUND, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClimateNotFoundException.class)
    public BaseErrorResponse handle_ClimateNotFoundException(ClimateNotFoundException e) {
        log.error("[handle_ClimateNotFoundException]", e);
        return new BaseErrorResponse(CLIMATE_NOT_FOUND, e.getMessage());
    }
}
