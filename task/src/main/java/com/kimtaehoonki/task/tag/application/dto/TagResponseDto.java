package com.kimtaehoonki.task.tag.application.dto;

import com.kimtaehoonki.task.colorcode.ColorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TagResponseDto {
    private Long id;
    private String name;
    private ColorCode colorCode;
}
