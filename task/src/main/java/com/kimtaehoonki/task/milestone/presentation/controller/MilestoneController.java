package com.kimtaehoonki.task.milestone.presentation.controller;

import com.kimtaehoonki.task.milestone.presentation.dto.DeleteMilestoneResponseDto;
import com.kimtaehoonki.task.milestone.presentation.dto.RegisterMilestoneRequestDto;
import com.kimtaehoonki.task.milestone.presentation.dto.RegisterMilestoneResponseDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MilestoneController {
    /**
     * 마일스톤을 등록한다.
     *
     * @param requestDto @RequestBody
     * @return RegisterMilestoneResponseDto
     */
    @PostMapping("/milestones")
    public RegisterMilestoneResponseDto registerMilestone(
            @RequestBody RegisterMilestoneRequestDto requestDto) {
        return null;
    }

    /**
     * 마일스톤을 삭제한다.
     *
     * @param milestoneId @PathVariable
     * @return DeleteMilestoneResponseDto
     */
    @DeleteMapping("/milestones/{id}")
    public DeleteMilestoneResponseDto deleteMilestone(@PathVariable("id") Long milestoneId) {
        return null;
    }
}
