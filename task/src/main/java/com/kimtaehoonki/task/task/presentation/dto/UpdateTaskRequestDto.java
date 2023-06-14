package com.kimtaehoonki.task.task.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskRequestDto {
    private String title;
    private String contents;
}
