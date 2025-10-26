package Konkuk.U2E.domain.comment.dto.response;

import Konkuk.U2E.domain.comment.domain.Comment;

public record CommentInfo(
        Long userId,
        String name,
        Long commentId,
        String contents
) {
    public static CommentInfo of(Comment comment) {
        return new CommentInfo(comment.getUser().getUserId(), comment.getUser().getName(), comment.getCommentId(), comment.getContents());
    }
}