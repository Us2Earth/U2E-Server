package Konkuk.U2E.domain.pin.dto.response;

import Konkuk.U2E.domain.news.domain.ClimateProblem;
import Konkuk.U2E.domain.pin.domain.Pin;
import Konkuk.U2E.domain.pin.domain.Region;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public record PinInfo(
        Long pinId,
        BigDecimal latitude,
        BigDecimal longitude,
        boolean isLately,
        String region,
        List<ClimateProblem> climateProblem
) {
    public static PinInfo of(Pin pin, Region region, boolean isLately, List<ClimateProblem> climateProblemList) {
        return new PinInfo(pin.getPinId(), region.getLatitude(), region.getLongitude(), isLately, region.getName(), climateProblemList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PinInfo pinInfo = (PinInfo) o;
        return Objects.equals(pinId, pinInfo.pinId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pinId);
    }
}
