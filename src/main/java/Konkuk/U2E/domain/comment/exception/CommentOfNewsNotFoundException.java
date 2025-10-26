package Konkuk.U2E.domain.comment.exception;

import Konkuk.U2E.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class CommentOfNewsNotFoundException extends RuntimeException {
    private final ResponseStatus exceptionStatus;


    public CommentOfNewsNotFoundException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
