package Konkuk.U2E.domain.news.exception;

import Konkuk.U2E.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class NewsNotFoundException extends RuntimeException {

    private final ResponseStatus exceptionStatus;

    public NewsNotFoundException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
