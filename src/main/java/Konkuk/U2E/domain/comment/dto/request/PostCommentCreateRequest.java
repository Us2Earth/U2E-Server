package Konkuk.U2E.domain.comment.dto.request;

public record PostCommentCreateRequest(
        Long newsId,
        String contents
) { }
