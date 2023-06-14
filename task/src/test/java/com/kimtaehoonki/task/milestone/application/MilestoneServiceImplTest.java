package com.kimtaehoonki.task.milestone.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kimtaehoonki.task.exception.impl.AuthorizedException;
import com.kimtaehoonki.task.exception.impl.MilestoneNotFoundException;
import com.kimtaehoonki.task.exception.impl.ProjectNotFoundException;
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
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class MilestoneServiceImplTest {
    @Mock
    MilestoneRepository milestoneRepository;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    AccountRestTemplate accountRt;

    @Mock
    MemberInProjectRepository memberInProjectRepository;

    @InjectMocks
    MilestoneServiceImpl milestoneService;

    @Test
    void 마일스톤을_등록한다() throws NoSuchFieldException, IllegalAccessException {
        Project project = Project.make(1, null, null);
        Milestone milestone = Milestone.create(project, "",
            LocalDate.of(2023, 9, 1),
            LocalDate.of(2023, 10, 1));

        Class<Milestone> milestoneClazz = Milestone.class;
        Field idField = milestoneClazz.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(milestone, 10L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(milestoneRepository.save(any())).thenReturn(milestone);
        when(memberInProjectRepository.existsByProject_idAndMemberId(any(), anyInt()))
            .thenReturn(true);

        Long registeredId = milestoneService.registerMilestone(
            new RegisterMilestoneRequestDto("야야", 1L,
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 10, 1)), 1);

        assertThat(registeredId).isEqualTo(10L);
        verify(projectRepository, times(1)).findById(any());
        verify(milestoneRepository, times(1)).save(any());
    }

    @Test
    void 마일스톤을_등록할때_해당_멤버가_프로젝트에_속해있지_않으면_예외를_반환한다()
        throws NoSuchFieldException, IllegalAccessException {
        Project project = Project.make(1, null, null);
        Milestone milestone = Milestone.create(project, "",
            LocalDate.of(2023, 9, 1),
            LocalDate.of(2023, 10, 1));

        Class<Milestone> milestoneClazz = Milestone.class;
        Field idField = milestoneClazz.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(milestone, 10L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(memberInProjectRepository.existsByProject_idAndMemberId(any(), anyInt()))
            .thenReturn(false);

        RegisterMilestoneRequestDto dto = new RegisterMilestoneRequestDto("야야", 1L,
            LocalDate.of(2023, 9, 1),
            LocalDate.of(2023, 10, 1));

        assertThatThrownBy(() -> milestoneService.registerMilestone(dto, 1))
            .isInstanceOf(AuthorizedException.class);

        verify(projectRepository, times(1)).findById(any());
        verify(milestoneRepository, never()).save(any());
    }

    @Test
    void 마일스톤을_등록할때_프로젝트가_없으면_예외를_반환한다() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
            milestoneService.registerMilestone(
                new RegisterMilestoneRequestDto("야야", 1L,
                    LocalDate.of(2023, 9, 1),
                    LocalDate.of(2023, 10, 1)),
                1))
            .isInstanceOf(ProjectNotFoundException.class);

        verify(projectRepository, times(1)).findById(any());
        verify(milestoneRepository, never()).save(any());
    }

    @Test
    void 종료일이_시작일보다_빠르면_예외를_반환한다() {
        assertThatThrownBy(() ->
            milestoneService.registerMilestone(
                new RegisterMilestoneRequestDto("야야", 1L,
                    LocalDate.of(2023, 9, 1),
                    LocalDate.of(2010, 10, 10)),
                1))
            .isInstanceOf(StartDateLaterThanEndDateException.class);
    }

    @Test
    void 마일스톤을_삭제한다() {
        Project project = Project.make(1, null, null);

        Milestone milestone = Milestone.create(project, "",
            LocalDate.of(2023,9,10),
            LocalDate.of(2023, 10, 10));

        when(milestoneRepository.findById(any())).thenReturn(Optional.of(milestone));
        when(projectRepository.findById(any())).thenReturn(Optional.of(project));
        when(memberInProjectRepository.existsByProject_idAndMemberId(any(), anyInt()))
            .thenReturn(true);

        milestoneService.deleteMilestone(10L, 1);

        verify(milestoneRepository, times(1)).findById(any());
        verify(milestoneRepository, times(1)).delete(any());
    }

    @Test
    void 마일스톤을_삭제할때_해당_프로젝트에_소속된_사람이_아니면_예외를_반환한다() {
        Project project = Project.make(1, null, null);
        Milestone milestone = Milestone.create(project, "",
            LocalDate.of(2023,9,10),
            LocalDate.of(2023, 10, 10));

        when(milestoneRepository.findById(any())).thenReturn(Optional.of(milestone));
        when(projectRepository.findById(any())).thenReturn(Optional.of(project));
        when(memberInProjectRepository.existsByProject_idAndMemberId(any(), anyInt()))
            .thenReturn(false);

        assertThatThrownBy(() -> milestoneService.deleteMilestone(10L, 1))
            .isInstanceOf(AuthorizedException.class);
    }

    @Test
    void 마일스톤을_삭제할때_해당_마일스톤이_없으면_예외를_반환한다() {
        when(milestoneRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
            milestoneService.deleteMilestone(1L, 1))
            .isInstanceOf(MilestoneNotFoundException.class);

        verify(milestoneRepository, times(1)).findById(any());
        verify(milestoneRepository, never()).delete(any());
    }

    @Test
    void 프로젝트_아이디로_마일스톤을_조회한다() {
        Milestone milestone = Milestone.create(null, "1주차",
            LocalDate.of(2021, 2, 3),
            LocalDate.of(2021, 3, 3));
        MilestoneResponseDto milestoneResponseDto = MilestoneResponseDto.from(milestone);
        when(milestoneRepository.findAllByProject_id(any()))
            .thenReturn(List.of(milestoneResponseDto));

        GetMilestonesByProjectIdResponseDto result =
            milestoneService.getMilestonesByProjectId(1L);

        assertThat(result.getMilestones()).contains(milestoneResponseDto);
        assertThat(result.getMilestones()).hasSize(1);
    }
}