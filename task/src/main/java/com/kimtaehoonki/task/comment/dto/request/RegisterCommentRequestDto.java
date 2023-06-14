package com.kimtaehoonki.task.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterCommentRequestDto {
    private String contents;
    private Long taskId;
}
