package com.kimtaehoonki.task.milestone.application;

import com.kimtaehoonki.task.milestone.presentation.dto.RegisterMilestoneRequestDto;

public interface MilestoneService {
    void registerMilestone(RegisterMilestoneRequestDto dto);
    void deleteMilestone(Long milestoneId);

}
