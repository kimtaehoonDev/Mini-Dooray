package com.kimtaehoonki.task.milestone.application;

import com.kimtaehoonki.task.milestone.dto.RegisterMilestoneRequestDto;
import com.kimtaehoonki.task.project.presentation.dto.response.GetMilestonesByProjectIdResponseDto;

public interface MilestoneService {
    Long registerMilestone(RegisterMilestoneRequestDto dto, Integer memberId);
    void deleteMilestone(Long milestoneId, Integer memberId);

    GetMilestonesByProjectIdResponseDto getMilestonesByProjectId(Long projectId);
}
