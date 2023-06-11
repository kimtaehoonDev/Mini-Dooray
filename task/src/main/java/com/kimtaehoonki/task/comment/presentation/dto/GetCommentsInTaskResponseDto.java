package com.kimtaehoonki.task.comment.presentation.dto;

import com.kimtaehoonki.task.comment.application.dto.response.CommentResponseDto;
import java.util.List;

public class GetCommentsInTaskResponseDto {
    private final List<CommentResponseDto> comments;

    public GetCommentsInTaskResponseDto(List<CommentResponseDto> comments) {
        this.comments = comments;
    }
}
