package com.kimtaehoonki.task.milestone.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterMilestoneRequestDto {
    private String name;
    private Long projectId;
    private LocalDate startDate;
    private LocalDate endDate;
}
