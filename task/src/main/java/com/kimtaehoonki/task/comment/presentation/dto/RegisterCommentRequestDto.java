package com.kimtaehoonki.task.comment.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterCommentRequestDto {
    private String contents;
    private Long taskId;
}
