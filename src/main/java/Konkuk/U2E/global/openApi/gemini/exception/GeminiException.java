package Konkuk.U2E.global.openApi.gemini.exception;

import Konkuk.U2E.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class GeminiException extends RuntimeException{
    private final ResponseStatus exceptionStatus;

    protected GeminiException(ResponseStatus status, String detail) {
        super(detail == null ? status.getMessage() : detail);
        this.exceptionStatus = status;
    }

}
