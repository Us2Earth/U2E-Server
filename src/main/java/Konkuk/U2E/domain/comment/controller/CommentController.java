package Konkuk.U2E.domain.comment.controller;

import Konkuk.U2E.domain.comment.dto.request.PostCommentCreateRequest;
import Konkuk.U2E.domain.comment.dto.response.GetCommentsResponse;
import Konkuk.U2E.domain.comment.service.CommentService;
import Konkuk.U2E.global.annotation.LoginUser;
import Konkuk.U2E.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{newsId}")
    public BaseResponse<GetCommentsResponse> getComments(@PathVariable("newsId") Long newsId) {
        log.info("댓글 조회 요청, newsId: {}", newsId);
        return BaseResponse.ok(commentService.getCommentInfo(newsId));
    }

    @PostMapping
    public BaseResponse<Void> postComments(@LoginUser String username, @RequestBody PostCommentCreateRequest request) {
        log.info("댓글 작성 요청, username: {}, request: {}", username, request);
        commentService.createComment(username, request);
        return BaseResponse.ok(null);
    }
}
