package com.kimtaehoonki.task.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateCommentRequestDto {
    private final String contents;
}
