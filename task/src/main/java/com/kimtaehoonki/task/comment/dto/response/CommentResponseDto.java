package com.kimtaehoonki.task.comment.dto.response;

import java.time.LocalDateTime;

public interface CommentResponseDto {
    Long getId();
    LocalDateTime getCreatedAt();
    Integer getWriterId();
    String getWriterName();
    String getContents();

}
