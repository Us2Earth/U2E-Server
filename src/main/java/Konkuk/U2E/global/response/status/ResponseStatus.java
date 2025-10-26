package Konkuk.U2E.global.response.status;

import org.springframework.http.HttpStatus;

public interface ResponseStatus {

    boolean getSuccess();
    int getCode();
    String getMessage();
}
