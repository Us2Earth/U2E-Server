package Konkuk.U2E.global.openApi.gemini.exception;

import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.GEMINI_INVALID_RESPONSE;

public class GeminiInvalidResponseException extends GeminiException {
    public GeminiInvalidResponseException(String detail) {
        super(GEMINI_INVALID_RESPONSE, detail);
    }
}
