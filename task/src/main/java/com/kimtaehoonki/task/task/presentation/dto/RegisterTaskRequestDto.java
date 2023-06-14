package com.kimtaehoonki.task.task.presentation.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterTaskRequestDto {
    private Long projectId;
    private Long milestoneId;
    private String title;
    private String content;
    private List<Long> tagIds;
}
