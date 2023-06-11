package com.kimtaehoonki.task.comment.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateCommentRequestDto {
    private final String contents;
}
