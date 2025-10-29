package Konkuk.U2E.global.openApi.gemini.exception;

import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.GEMINI_CALL_FAILED;

public class GeminiCallFailedException extends GeminiException {
    public GeminiCallFailedException(String detail) {
        super(GEMINI_CALL_FAILED, detail);
    }
}
