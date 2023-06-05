package com.kimtaehoonki.task.project.presentation.dto;

import com.kimtaehoonki.task.ProjectStatus;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ShowProjectResponseDto {
    private long id;
    private int adminId;
    private String name;
    private String description;
    private ProjectStatus status;
}
