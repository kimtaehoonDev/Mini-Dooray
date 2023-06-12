package com.kimtaehoonki.task.milestone.application;

import com.kimtaehoonki.task.exception.impl.MilestoneNotFoundException;
import com.kimtaehoonki.task.exception.impl.ProjectNotFoundException;
import com.kimtaehoonki.task.milestone.domain.Milestone;
import com.kimtaehoonki.task.milestone.domain.MilestoneRepository;
import com.kimtaehoonki.task.milestone.presentation.dto.RegisterMilestoneRequestDto;
import com.kimtaehoonki.task.project.domain.ProjectRepository;
import com.kimtaehoonki.task.project.domain.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public Long registerMilestone(RegisterMilestoneRequestDto dto) {
        Project project = projectRepository.findById(dto.getProjectId())
            .orElseThrow(ProjectNotFoundException::new);

        Milestone milestone =
            Milestone.create(project, dto.getName(), dto.getStartDate(), dto.getEndDate());
        Milestone savedMilestone = milestoneRepository.save(milestone);
        return savedMilestone.getId();
    }

    @Override
    @Transactional
    public void deleteMilestone(Long milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
            .orElseThrow(MilestoneNotFoundException::new);
        milestoneRepository.delete(milestone);
    }
}
