package Konkuk.U2E.domain.pin.dto.response;

import java.util.List;

public record GetPinListResponse(
        List<PinInfo> pinList
) {
    public static GetPinListResponse of(List<PinInfo> pinList) {
        return new GetPinListResponse(pinList);
    }
}
