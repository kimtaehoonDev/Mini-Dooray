package com.kimtaehoonki.task.project.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateProjectRequestDto {
    private int adminId;
    private String name;
    private String description;
}
