package Konkuk.U2E.domain.news.dto.response;

import java.util.List;

public record GetLatelyNewsResponse(
        List<LatelyNews> latelyNewsList
) {
    public static GetLatelyNewsResponse of(List<LatelyNews> latelyNewsList) {
        return new GetLatelyNewsResponse(latelyNewsList);
    }
}
