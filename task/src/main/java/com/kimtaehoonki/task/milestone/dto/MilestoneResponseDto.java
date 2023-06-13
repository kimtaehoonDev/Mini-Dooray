package com.kimtaehoonki.task.milestone.dto;

import com.kimtaehoonki.task.milestone.domain.Milestone;
import com.kimtaehoonki.task.milestone.domain.MilestoneRepository;
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

    public static MilestoneResponseDto from(Milestone milestone) {
        return new MilestoneResponseDto(milestone.getId(), milestone.getName(),
            milestone.getStartDate(), milestone.getEndDate());
    }
}
