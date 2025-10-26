package Konkuk.U2E.domain.pin.exception;

import Konkuk.U2E.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class ClimateNotFoundException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public ClimateNotFoundException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
