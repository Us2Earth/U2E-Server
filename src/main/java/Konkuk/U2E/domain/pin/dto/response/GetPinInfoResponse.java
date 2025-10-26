package Konkuk.U2E.domain.pin.dto.response;

import java.util.List;

public record GetPinInfoResponse(
        List<NewsInfo> newsList
) {
    public static GetPinInfoResponse of(List<NewsInfo> newsList) {
        return new GetPinInfoResponse(newsList);
    }
}
