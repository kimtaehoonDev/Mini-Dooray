package com.kimtaehoonki.task.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterTagRequestDto {
    private String name;
    private Long projectId;
}
