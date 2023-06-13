package com.kimtaehoonki.task.milestone.application;

import com.kimtaehoonki.task.milestone.presentation.dto.RegisterMilestoneRequestDto;
import com.kimtaehoonki.task.project.presentation.dto.GetMilestonesByProjectIdResponseDto;

public interface MilestoneService {
    Long registerMilestone(RegisterMilestoneRequestDto dto);
    void deleteMilestone(Long milestoneId);

    GetMilestonesByProjectIdResponseDto getMilestonesByProjectId(Long projectId);
}
