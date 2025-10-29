package Konkuk.U2E.global.openApi.gemini.exception.handler;


import Konkuk.U2E.global.openApi.gemini.exception.GeminiCallFailedException;
import Konkuk.U2E.global.openApi.gemini.exception.GeminiInvalidResponseException;
import Konkuk.U2E.global.openApi.gemini.exception.GeminiMissingApiKeyException;
import Konkuk.U2E.global.response.BaseErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Slf4j
@Order(HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GeminiControllerAdvice {

    // 외부 API 호출 실패
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(GeminiCallFailedException.class)
    public BaseErrorResponse handleGeminiCallFailed(GeminiCallFailedException e) {
        log.error("[GeminiCallFailed]", e);
        return new BaseErrorResponse(e.getExceptionStatus(), e.getMessage());
    }

    // 파싱 실패
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(GeminiInvalidResponseException.class)
    public BaseErrorResponse handleGeminiInvalidResponse(GeminiInvalidResponseException e) {
        log.error("[GeminiInvalidResponse]", e);
        return new BaseErrorResponse(e.getExceptionStatus(), e.getMessage());
    }

    // 설정/키 누락
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(GeminiMissingApiKeyException.class)
    public BaseErrorResponse handleGeminiMissingApiKey(GeminiMissingApiKeyException e) {
        log.error("[GeminiMissingApiKey]", e);
        return new BaseErrorResponse(e.getExceptionStatus(), e.getMessage());
    }
}
