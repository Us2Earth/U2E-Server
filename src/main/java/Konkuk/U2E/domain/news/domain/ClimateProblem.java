package Konkuk.U2E.domain.news.domain;

import Konkuk.U2E.domain.pin.exception.ClimateNotFoundException;
import lombok.Getter;

import static Konkuk.U2E.global.response.status.BaseExceptionResponseStatus.*;

@Getter
public enum ClimateProblem {

    TEMPERATURE_RISE("온도 상승"),
    HEAVY_RAIN_OR_FLOOD("폭우/홍수"),
    FINE_DUST("미세먼지"),
    DROUGHT_OR_DESERTIFICATION("가뭄/사막화"),
    SEA_LEVEL_RISE("해수면 상승"),
    TYPHOON_OR_TORNADO("태풍/토네이도"),

    // 기타
    WILDFIRE("산불"),
    EARTHQUAKE("지진"),
    DEFORESTATION("삼림 벌채"),
    BIODIVERSITY_LOSS("생물 다양성 감소");


    private final String climateProblem;

    ClimateProblem(String climateProblem) {
        this.climateProblem = climateProblem;
    }

    public static ClimateProblem fromString(String climate) {
        for (ClimateProblem climateProblem : ClimateProblem.values()) {
            if (climateProblem.toString().equals(climate)) {
                return climateProblem;
            }
        }
        throw new ClimateNotFoundException(CLIMATE_NOT_FOUND);
    }
}
