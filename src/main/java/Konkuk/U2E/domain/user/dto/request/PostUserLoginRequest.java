package Konkuk.U2E.domain.user.dto.request;

public record PostUserLoginRequest(
        String name,
        String password
) {}