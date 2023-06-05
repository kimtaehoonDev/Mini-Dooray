package com.kimtaehoonki.task.project.presentation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateProjectRequestDto {
    private int adminId;
    private String name;
    private String description;
}
