package com.kimtaehoonki.task.comment.presentation.controller;

import com.kimtaehoonki.task.comment.presentation.dto.DeleteCommentResponseDto;
import com.kimtaehoonki.task.comment.presentation.dto.GetCommentsInPostResponseDto;
import com.kimtaehoonki.task.comment.presentation.dto.RegisterCommentRequestDto;
import com.kimtaehoonki.task.comment.presentation.dto.RegisterCommentResponseDto;
import com.kimtaehoonki.task.comment.presentation.dto.UpdateCommentResponseDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    /**
     * 댓글을 단다.
     *
     * @param requestDto @RequestBody
     * @return RegisterCommentResponseDto
     */
    @PostMapping("/comments")
    public RegisterCommentResponseDto registerComment(
            @RequestBody RegisterCommentRequestDto requestDto) {
        return null;
    }

    /**
     * 특정 게시물의 댓글들을 조회한다.
     *
     * @param taskId @RequestParam
     * @return GetCommentsInPostResponseDto
     */
    @GetMapping("/comments")
    public GetCommentsInPostResponseDto getCommentsInPost(@RequestParam("id") Long taskId) {
        return null;
    }

    /**
     * 댓글을 수정한다.
     *
     * @param commentId @PathVariable
     * @return UpdateCommentResponseDto
     */
    @PutMapping("/comments/{id}")
    public UpdateCommentResponseDto updateComment(@PathVariable("id") Long commentId) {
        return null;
    }

    /**
     * 댓글을 삭제한다.
     *
     * @param commentId @PathVariable
     * @return DeleteCommentResponseDto
     */
    @DeleteMapping("/comments/{id}")
    public DeleteCommentResponseDto deleteComment(@PathVariable("id") Long commentId) {
        return null;
    }
}
