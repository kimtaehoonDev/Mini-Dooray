package com.kimtaehoonki.task.task.presentation.dto;

import com.kimtaehoonki.task.task.domain.Task;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetTaskResponseDto {
    private Long projectId;
    private Integer indexInProject;
    private String title;
    private String contents;
    private Integer writerId;
    private String writerName;
    private LocalDateTime createdAt;

    public static GetTaskResponseDto to(Task task) {

        return new GetTaskResponseDto(
            task.getProject().getId(),
            task.getIndexInProject(),
            task.getTitle(),
            task.getContents(),
            task.getWriterId(),
            task.getWriterName(),
            task.getCreatedAt()
        );
    }
}
