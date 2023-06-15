package com.kimtaehoonki.task.milestone.application;

import com.kimtaehoonki.task.exception.impl.AuthorizedException;
import com.kimtaehoonki.task.exception.impl.MilestoneNotFoundException;
import com.kimtaehoonki.task.exception.impl.ProjectNotFoundException;
import com.kimtaehoonki.task.exception.impl.ResourceNameDuplicatedException;
import com.kimtaehoonki.task.exception.impl.StartDateLaterThanEndDateException;
import com.kimtaehoonki.task.member.AccountRestTemplate;
import com.kimtaehoonki.task.milestone.domain.Milestone;
import com.kimtaehoonki.task.milestone.domain.MilestoneRepository;
import com.kimtaehoonki.task.milestone.dto.MilestoneResponseDto;
import com.kimtaehoonki.task.milestone.dto.RegisterMilestoneRequestDto;
import com.kimtaehoonki.task.project.domain.repository.MemberInProjectRepository;
import com.kimtaehoonki.task.project.domain.repository.ProjectRepository;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.project.presentation.dto.response.GetMilestonesByProjectIdResponseDto;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final AccountRestTemplate accountRt;
    private final MemberInProjectRepository memberInProjectRepository;

    @Override
    @Transactional
    public Long registerMilestone(RegisterMilestoneRequestDto dto, Integer memberId) {
        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();

        if (startDate.isAfter(endDate)) {
            throw new StartDateLaterThanEndDateException();
        }

        accountRt.validateMemberExists(memberId);

        Project project = projectRepository.findById(dto.getProjectId())
            .orElseThrow(ProjectNotFoundException::new);

        boolean isMemberInProject =
            memberInProjectRepository.existsByProject_idAndMemberId(project.getId(), memberId);
        if (!isMemberInProject) {
            throw new AuthorizedException();
        }

        boolean isDuplicatedName = milestoneRepository.existsByName(dto.getName());
        if (isDuplicatedName) {
            throw new ResourceNameDuplicatedException();
        }

        Milestone milestone =
            Milestone.create(project, dto.getName(), startDate, endDate);
        Milestone savedMilestone = milestoneRepository.save(milestone);
        return savedMilestone.getId();
    }

    @Override
    @Transactional
    public void deleteMilestone(Long milestoneId, Integer memberId) {
        accountRt.validateMemberExists(memberId);

        Milestone milestone = milestoneRepository.findById(milestoneId)
            .orElseThrow(MilestoneNotFoundException::new);

        Long projectId = milestone.getProject().getId();
        Project project = projectRepository.findById(projectId)
            .orElseThrow(ProjectNotFoundException::new);
        boolean isMemberInProject =
            memberInProjectRepository.existsByProject_idAndMemberId(project.getId(), memberId);
        if (!isMemberInProject) {
            throw new AuthorizedException();
        }

        milestoneRepository.delete(milestone);
    }

    @Override
    public GetMilestonesByProjectIdResponseDto getMilestonesByProjectId(Long projectId) {
        List<MilestoneResponseDto> milestones = milestoneRepository.findAllByProject_id(projectId);
        return new GetMilestonesByProjectIdResponseDto(milestones);

    }
}
