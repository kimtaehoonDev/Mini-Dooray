package com.kimtaehoonki.task.tag.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterTagRequestDto {
    @NotBlank
    private String name;

    @NotNull
    private Long projectId;
}
