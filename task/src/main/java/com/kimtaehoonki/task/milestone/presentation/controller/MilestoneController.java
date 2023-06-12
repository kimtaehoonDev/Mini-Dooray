package com.kimtaehoonki.task.milestone.presentation.controller;

import com.kimtaehoonki.task.milestone.application.MilestoneService;
import com.kimtaehoonki.task.milestone.presentation.dto.DeleteMilestoneResponseDto;
import com.kimtaehoonki.task.milestone.presentation.dto.RegisterMilestoneRequestDto;
import com.kimtaehoonki.task.milestone.presentation.dto.RegisterMilestoneResponseDto;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MilestoneController {
    private final MilestoneService milestoneService;

    /**
     * 마일스톤을 등록한다.
     *
     */
    @PostMapping("/milestones")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerMilestone(
            @RequestBody RegisterMilestoneRequestDto dto) {
        milestoneService.registerMilestone(dto);
    }

    /**
     * 마일스톤을 삭제한다.
     *
     */
    @DeleteMapping("/milestones/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMilestone(@PathVariable("id") Long milestoneId) {
        milestoneService.deleteMilestone(milestoneId);

    }
}
