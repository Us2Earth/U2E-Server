package Konkuk.U2E.global.response.status;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BaseExceptionResponseStatus implements ResponseStatus {

    SUCCESS(20000, "요청에 성공했습니다."),
    BAD_REQUEST(40000, "유효하지 않은 요청입니다."),
    NOT_FOUND(40400, "존재하지 않는 API입니다."),
    INTERNAL_SERVER_ERROR(50000, "서버 내부 오류입니다."),

    /**
     * 60000 : Comment
     */
    COMMENT_OF_NEWS_NOT_FOUND(60000, "댓글에 대한 뉴스를 찾을 수 없습니다."),

    /**
     * 70000 : News
     */
    NEWS_NOT_FOUND(70000, "알맞은 뉴스를 찾을 수 없습니다."),

    /**
     * 80000 : Pin
     */
    PINNEWS_NOT_FOUND(80000, "핀에 대한 뉴스를 찾을 수 없습니다."),
    NEWSPIN_NOT_FOUND(80001, "뉴스에 대한 핀을 찾을 수 없습니다."),
    INVALID_PARAM(80002, "유효하지 않은 파라미터입니다."),
    CLIMATE_NOT_FOUND(80003, "매칭되는 기후 뉴스를 찾을 수 없습니다."),

    /**
     * 90000 : User
     */
    DUPLICATE_USER(90000, "중복된 아이디 입니다."),
    INVALID_ACCESS_TOKEN(90001, "유효하지 않은 액세스 토큰입니다."),

    /**
     * 100000 : External API (Gemini)
     */
    GEMINI_CALL_FAILED(100000, "Gemini API 호출에 실패했습니다."),
    GEMINI_INVALID_RESPONSE(100001, "Gemini 응답 파싱에 실패했습니다."),
    GEMINI_MISSING_API_KEY(100002, "Gemini API Key가 설정되지 않았습니다.")
    ;

    private final boolean success = false;
    private final int code;
    private final String message;

    @Override
    public boolean getSuccess() {
        return success;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
