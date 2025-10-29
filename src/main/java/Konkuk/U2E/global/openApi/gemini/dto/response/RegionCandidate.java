package Konkuk.U2E.global.openApi.gemini.dto.response;


import java.math.BigDecimal;

public record RegionCandidate(
        String name,
        BigDecimal latitude,
        BigDecimal longitude
) {}
