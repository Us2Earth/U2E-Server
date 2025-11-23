package Konkuk.U2E.domain.pin.dto.response;

import java.util.Comparator;
import java.util.List;

public record GetPinListResponse(
        List<PinInfo> pinList
) {
    public static GetPinListResponse of(List<PinInfo> pinList) {
        List<PinInfo> result = pinList.stream()
                .sorted(Comparator.comparing(PinInfo::pinId).reversed()) // pinId 기준 내림차순
                .limit(70)                                               // 최대 70개
                .toList();
        return new GetPinListResponse(result);
    }
}
