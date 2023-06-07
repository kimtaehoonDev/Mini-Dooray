package com.kimtaehoonki.task.project.presentation.dto.response;

import lombok.Getter;

@Getter
public class CreateProjectResponseDto {
    private long projectId;

    public CreateProjectResponseDto(long projectId) {
        this.projectId = projectId;
    }
}
