package Konkuk.U2E.global.openApi.gemini.dto.response;

import java.util.List;

public record AiResponse(
        String summary,                    // 기사 요약
        String solution,                   // 실행 가능한 핵심 솔루션
        List<RelatedArticle> related,      // 관련 뉴스 (최대 3)
        List<RegionCandidate> regions ,     // 지역 후보 (최대 3, 이름+위도+경도)
        List<String> climateProblems       // 기후문제 enum 이름 리스트 (예: ["WILDFIRE","HEAVY_RAIN_OR_FLOOD"])
) {
}
