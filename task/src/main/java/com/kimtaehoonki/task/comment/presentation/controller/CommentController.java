package com.kimtaehoonki.task.comment.presentation.controller;

import com.kimtaehoonki.task.comment.application.CommentService;
import com.kimtaehoonki.task.comment.application.dto.response.CommentResponseDto;
import com.kimtaehoonki.task.comment.presentation.dto.DeleteCommentResponseDto;
import com.kimtaehoonki.task.comment.presentation.dto.GetCommentsInTaskResponseDto;
import com.kimtaehoonki.task.comment.presentation.dto.RegisterCommentRequestDto;
import com.kimtaehoonki.task.comment.presentation.dto.UpdateCommentRequestDto;
import com.kimtaehoonki.task.comment.presentation.dto.UpdateCommentResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글을 단다.
     */
    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerComment(
        @RequestBody RegisterCommentRequestDto requestDto,
        @CookieValue Integer memberId) {
        String contents = requestDto.getContents();
        commentService.comment(contents, memberId);
    }

    /**
     * 특정 게시물의 댓글들을 조회한다.
     */
    @GetMapping("/comments")
    public GetCommentsInTaskResponseDto getCommentsInTask(@RequestParam("id") Long taskId) {
        List<CommentResponseDto> result = commentService.getCommentsInTask(taskId);
        return new GetCommentsInTaskResponseDto(result);
    }

    /**
     * 댓글을 수정한다.
     */
    @PutMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateComment(@PathVariable("id") Long commentId,
                              @CookieValue Integer memberId,
                              @RequestBody UpdateCommentRequestDto dto) {
        String contents = dto.getContents();
        commentService.updateComment(commentId, contents, memberId);
    }

    /**
     * 댓글을 삭제한다.
     *
     */
    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("id") Long commentId,
                              @CookieValue Integer memberId) {
        commentService.deleteComment(commentId, memberId);
    }
}
