package com.kimtaehoonki.task.comment.dto.response;

import java.util.List;

public class GetCommentsInTaskResponseDto {
    private final List<CommentResponseDto> comments;

    public GetCommentsInTaskResponseDto(List<CommentResponseDto> comments) {
        this.comments = comments;
    }
}
