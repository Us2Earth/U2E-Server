package Konkuk.U2E.global.openApi.gemini.dto.request;

public record AiNewsRequest(
        String body,            // 분석할 뉴스 본문
        String locale           // "ko" or "en" (옵션)
) {}
