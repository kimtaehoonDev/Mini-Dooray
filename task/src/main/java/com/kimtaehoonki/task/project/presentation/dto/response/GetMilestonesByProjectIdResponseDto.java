package com.kimtaehoonki.task.project.presentation.dto.response;

import com.kimtaehoonki.task.milestone.dto.MilestoneResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetMilestonesByProjectIdResponseDto {
    List<MilestoneResponseDto> milestones;
}
