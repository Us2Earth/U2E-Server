package Konkuk.U2E.global.openApi.gemini.exception;

import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.GEMINI_MISSING_API_KEY;

public class GeminiMissingApiKeyException extends GeminiException {
    public GeminiMissingApiKeyException() {
        super(GEMINI_MISSING_API_KEY, null);
    }
}
