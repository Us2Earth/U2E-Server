package Konkuk.U2E.domain.user.dto.response;

public record PostUserLoginResponse(
        Long userId,
        String token
) {
    public static PostUserLoginResponse of(Long userId, String token) {
        return new PostUserLoginResponse(userId, token);
    }
}