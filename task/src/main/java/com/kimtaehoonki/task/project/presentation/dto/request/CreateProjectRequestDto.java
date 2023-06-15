package com.kimtaehoonki.task.project.presentation.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateProjectRequestDto {
    @NotNull
    private Integer adminId;

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
