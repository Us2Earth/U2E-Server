package Konkuk.U2E.domain.user.exception;

import Konkuk.U2E.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class InvalidAccessTokenException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public InvalidAccessTokenException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
