package com.kimtaehoonki.task.task.application.dto;

import com.kimtaehoonki.task.milestone.domain.Milestone;
import com.kimtaehoonki.task.tag.ColorCode;
import com.kimtaehoonki.task.tag.domain.Tag;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class TaskPreview {
    private Long taskId;
    private Long projectId;
    private Integer indexInProject;
    private String title;
    private String writerName;
    private LocalDateTime createdAt;
    private MilestoneInGetTaskResponseDto milestone;
    private List<TagsInGetTaskResponseDto> tags;

    @QueryProjection
    public TaskPreview(Long taskId, Long projectId, Integer indexInProject, String title,
                       String writerName, LocalDateTime createdAt,
                       MilestoneInGetTaskResponseDto milestone,
                       List<TagsInGetTaskResponseDto> tags) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.indexInProject = indexInProject;
        this.title = title;
        this.writerName = writerName;
        this.createdAt = createdAt;
        this.milestone = milestone;
        this.tags = tags;
    }

    @Getter
    public static class MilestoneInGetTaskResponseDto {
        private Optional<String> name;

        @QueryProjection
        public MilestoneInGetTaskResponseDto(String name) {
            this.name = Optional.ofNullable(name);
        }
    }

    @Getter
    @ToString
    public static class TagsInGetTaskResponseDto {
        private String name;
        private ColorCode colorCode;

        @QueryProjection
        public TagsInGetTaskResponseDto(String name, ColorCode colorCode) {
            this.name = name;
            this.colorCode = colorCode;
        }
    }

}
