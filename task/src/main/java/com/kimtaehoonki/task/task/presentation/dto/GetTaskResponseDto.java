package com.kimtaehoonki.task.task.presentation.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetTaskResponseDto {
    private Long projectId;
    private Integer indexInProject;
    private String title;
    private String contents;
    private Integer writerId;
    private String writerName;
    private LocalDateTime createdAt;

}
