package Konkuk.U2E.domain.comment.dto.response;

import java.util.List;

public record GetCommentsResponse(
        List<CommentInfo> commentList
) {
    public static GetCommentsResponse of(List<CommentInfo> commentList) {
        return new GetCommentsResponse(commentList);
    }
}
