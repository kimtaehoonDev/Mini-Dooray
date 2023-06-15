package com.kimtaehoonki.task.task.presentation.dto;

import com.kimtaehoonki.task.tag.ColorCode;
import com.kimtaehoonki.task.tag.domain.Tag;
import com.kimtaehoonki.task.task.domain.Task;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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
    private List<TagInGetTaskResponseDto> tags;

    public static GetTaskResponseDto of(Task task, List<Tag> tags) {
        return new GetTaskResponseDto(
            task.getProject().getId(),
            task.getIndexInProject(),
            task.getTitle(),
            task.getContents(),
            task.getWriterId(),
            task.getWriterName(),
            task.getCreatedAt(),
            tags.stream()
                .map(tag -> new TagInGetTaskResponseDto(tag.getName(), tag.getColorCode()))
                .collect(Collectors.toList())
        );
    }

    @Getter
    @AllArgsConstructor
    public static class TagInGetTaskResponseDto {
        private String name;
        private ColorCode colorCode;
    }
}
