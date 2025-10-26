package Konkuk.U2E.domain.user.exception;

import Konkuk.U2E.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class DuplicateUserException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public DuplicateUserException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
