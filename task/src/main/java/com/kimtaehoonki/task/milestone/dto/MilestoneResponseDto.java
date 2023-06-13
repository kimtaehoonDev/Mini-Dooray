package com.kimtaehoonki.task.milestone.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MilestoneResponseDto {
    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;
}
