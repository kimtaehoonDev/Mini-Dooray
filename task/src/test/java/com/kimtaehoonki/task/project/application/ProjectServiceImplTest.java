package com.kimtaehoonki.task.project.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kimtaehoonki.task.project.ProjectStatus;
import com.kimtaehoonki.task.exception.impl.AlreadyProjectMemberException;
import com.kimtaehoonki.task.exception.impl.AuthorizedException;
import com.kimtaehoonki.task.exception.impl.MemberNotFoundException;
import com.kimtaehoonki.task.exception.impl.ProjectExitException;
import com.kimtaehoonki.task.exception.impl.ProjectNameDuplicateException;
import com.kimtaehoonki.task.exception.impl.ProjectNotFoundException;
import com.kimtaehoonki.task.member.AccountRestTemplate;
import com.kimtaehoonki.task.project.application.dto.response.ProjectDetail;
import com.kimtaehoonki.task.project.domain.repository.MemberInProjectRepository;
import com.kimtaehoonki.task.project.domain.repository.ProjectRepository;
import com.kimtaehoonki.task.project.domain.entity.MemberInProject;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.project.presentation.dto.request.CreateProjectRequestDto;
import java.lang.reflect.Field;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ProjectServiceImplTest {
    @MockBean
    ProjectRepository projectRepository;
    @MockBean
    MemberInProjectRepository memberInProjectRepository;
    @MockBean
    AccountRestTemplate accountRt;

    @Autowired
    ProjectServiceImpl projectService;

    @Test
    void 프로젝트를_생성한다() throws NoSuchFieldException, IllegalAccessException {
        Project project = makeTestProject(1L, 1, null,
            null, ProjectStatus.ACTIVATION);

        doNothing().when(accountRt).validateMemberExists(anyInt());
        when(projectRepository.existsByName("name")).thenReturn(false);
        when(projectRepository.save(any())).thenReturn(project);

        projectService.createProject(new CreateProjectRequestDto(1, "name", "설명"));

        verify(accountRt, times(1)).validateMemberExists(anyInt());
        verify(projectRepository, times(1)).existsByName("name");
        verify(projectRepository, times(1)).save(any());
        verify(memberInProjectRepository, times(1)).save(any());
    }


    @Test
    void 프로젝트_생성시_존재하지_않는_회원인_경우_예외를_반환한다() {
        doThrow(MemberNotFoundException.class)
            .when(accountRt).validateMemberExists(anyInt());

        assertThatThrownBy(() ->
            projectService.createProject(
                new CreateProjectRequestDto(1, "name", "설명"))
        ).isInstanceOf(MemberNotFoundException.class);

        verify(accountRt, times(1)).validateMemberExists(anyInt());
        verify(projectRepository, never()).existsByName(any());
        verify(projectRepository, never()).save(any());
        verify(memberInProjectRepository, never()).save(any());

    }

    @Test
    void 중복된이름의_프로젝트가_있으면_예외를_반환한다() {
        doNothing().when(accountRt).validateMemberExists(anyInt());
        when(projectRepository.existsByName(any())).thenReturn(true);

        assertThatThrownBy(() ->
            projectService.createProject(
                new CreateProjectRequestDto(1, "name", "설명"))
        ).isInstanceOf(ProjectNameDuplicateException.class);

        verify(accountRt, times(1)).validateMemberExists(anyInt());
        verify(projectRepository, times(1)).existsByName(any());
        verify(projectRepository, never()).save(any());
        verify(memberInProjectRepository, never()).save(any());
    }

    @Test
    void 해당_유저가_속한_프로젝트_목록을_보여준다() {
        doNothing().when(accountRt).validateMemberExists(anyInt());

        projectService.showProjectsPreviewsBelongsToMember(1);

        verify(accountRt, times(1))
            .validateMemberExists(anyInt());
        verify(memberInProjectRepository, times(1))
            .findProjectsPreviewsUsingMemberId(anyInt());
    }

    @Test
    void 프로젝트를_가져올_대상_멤버가_존재하지않으면_예외를_반환한다() {
        doThrow(MemberNotFoundException.class)
            .when(accountRt).validateMemberExists(anyInt());

        assertThatThrownBy(() ->
            projectService.showProjectsPreviewsBelongsToMember(1)
        ).isInstanceOf(MemberNotFoundException.class);
        verify(memberInProjectRepository, never()).findProjectsPreviewsUsingMemberId(anyInt());
    }

    @Test
    void 프로젝트의_정보를_조회한다() {
        ProjectDetail projectDetail = makeTestProjectDetail(1L, 1, null,
            null, ProjectStatus.ACTIVATION);
        doNothing().when(accountRt)
            .validateMemberExists(anyInt());
        when(memberInProjectRepository.existsByProject_idAndMemberId(any(), anyInt()))
            .thenReturn(true);
        when(projectRepository.findById(any(), any())).thenReturn(Optional.of(projectDetail));

        ProjectDetail result = projectService.showProject(1L, 1);

        assertThat(result.getId()).isEqualTo(projectDetail.getId());
        verify(accountRt, times(1)).validateMemberExists(anyInt());
        verify(memberInProjectRepository, times(1))
            .existsByProject_idAndMemberId(any(), anyInt());
        verify(projectRepository, times(1))
            .findById(any(), any());
    }

    @Test
    void 프로젝트의_정보를_조회시_해당_프로젝트에_권한이_없으면_예외를_반환한다() {
        doThrow(MemberNotFoundException.class).when(accountRt)
            .validateMemberExists(anyInt());

        assertThatThrownBy(() -> projectService.showProject(1L, 1))
            .isInstanceOf(MemberNotFoundException.class);

        verify(accountRt, times(1)).validateMemberExists(anyInt());
        verify(memberInProjectRepository, never())
            .existsByProject_idAndMemberId(any(), anyInt());
        verify(projectRepository, never())
            .findById(any(), any());
    }

    @Test
    void 프로젝트의_정보를_조회시_사용자가_존재하지_않으면_예외를_반환한다() {
        doNothing().when(accountRt)
            .validateMemberExists(anyInt());
        when(memberInProjectRepository.existsByProject_idAndMemberId(any(), anyInt()))
            .thenReturn(false);

        assertThatThrownBy(() -> projectService.showProject(1L, 1))
            .isInstanceOf(AuthorizedException.class);

        verify(accountRt, times(1)).validateMemberExists(anyInt());
        verify(memberInProjectRepository, times(1))
            .existsByProject_idAndMemberId(any(), anyInt());
        verify(projectRepository, never())
            .findById(any(), any());
    }

    @Test
    void 프로젝트의_정보를_조회시_프로젝트가_존재하지_않으면_예외를_반환한다() {
        doNothing().when(accountRt)
            .validateMemberExists(anyInt());
        when(memberInProjectRepository.existsByProject_idAndMemberId(any(), anyInt()))
            .thenReturn(true);
        when(projectRepository.findById(any(), any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.showProject(1L, 1))
            .isInstanceOf(ProjectNotFoundException.class);

        verify(accountRt, times(1)).validateMemberExists(anyInt());
        verify(memberInProjectRepository, times(1))
            .existsByProject_idAndMemberId(any(), anyInt());
        verify(projectRepository, times(1))
            .findById(any(), any());
    }

    @Test
    void 프로젝트의_정보를_조회시_프로젝트가_종료되었으면_예외를_반환한다() {
        ProjectDetail projectDetail = makeTestProjectDetail(1L, 1, null,
            null, ProjectStatus.EXIT);

        doNothing().when(accountRt)
            .validateMemberExists(anyInt());
        when(memberInProjectRepository.existsByProject_idAndMemberId(any(), anyInt()))
            .thenReturn(true);
        when(projectRepository.findById(any(), any())).thenReturn(Optional.of(projectDetail));

        assertThatThrownBy(() -> projectService.showProject(1L, 1))
            .isInstanceOf(ProjectExitException.class);

        verify(accountRt, times(1)).validateMemberExists(anyInt());
        verify(memberInProjectRepository, times(1))
            .existsByProject_idAndMemberId(any(), anyInt());
        verify(projectRepository, times(1))
            .findById(any(), any());
    }

    @Test
    void 프로젝트의_정보를_조회시_프로젝트가_휴면상태여도_조회가_가능하다() {
        ProjectDetail projectDetail = makeTestProjectDetail(1L, 1, null,
            null, ProjectStatus.DORMANT);
        doNothing().when(accountRt)
            .validateMemberExists(anyInt());
        when(memberInProjectRepository.existsByProject_idAndMemberId(any(), anyInt()))
            .thenReturn(true);
        when(projectRepository.findById(any(), any())).thenReturn(Optional.of(projectDetail));

        ProjectDetail result = projectService.showProject(1L, 1);

        assertThat(result.getId()).isEqualTo(projectDetail.getId());
        verify(accountRt, times(1)).validateMemberExists(anyInt());
        verify(memberInProjectRepository, times(1))
            .existsByProject_idAndMemberId(any(), anyInt());
        verify(projectRepository, times(1))
            .findById(any(), any());
    }

    @Test
    void 프로젝트_정보를_업데이트한다() throws NoSuchFieldException, IllegalAccessException {
        Project project = makeTestProject(1L, 1, null, null,
            ProjectStatus.ACTIVATION);
        doNothing().when(accountRt).validateMemberExists(anyInt());
        when(projectRepository.findById(any())).thenReturn(Optional.of(project));

        //프로젝트 상태가 바뀐걸 테스트한다
        projectService.updateProjectStatus(1L, 1, ProjectStatus.DORMANT);

        assertThat(project.getStatus()).isEqualTo(ProjectStatus.DORMANT);
        verify(accountRt, times(1)).validateMemberExists(anyInt());
    }

    @Test
    void 프로젝트_정보를_업데이트할때_실행자가_존재하지_않으면_예외를_반환한다() {
        doThrow(MemberNotFoundException.class).when(accountRt).validateMemberExists(anyInt());

        assertThatThrownBy(
                () -> projectService.updateProjectStatus(1L, 1, ProjectStatus.DORMANT))
            .isInstanceOf(MemberNotFoundException.class);
        verify(accountRt, times(1)).validateMemberExists(anyInt());
    }

    @Test
    void 프로젝트_정보를_업데이트할때_해당프로젝트의_어드민이_아니면_예외를_반환한다()
        throws NoSuchFieldException, IllegalAccessException {
        Project project = makeTestProject(1L, 1, null, null,
            ProjectStatus.ACTIVATION);
        doNothing().when(accountRt).validateMemberExists(anyInt());
        when(projectRepository.findById(any())).thenReturn(Optional.of(project));

        assertThatThrownBy(
            () -> projectService.updateProjectStatus(1L, 1000, ProjectStatus.DORMANT))
            .isInstanceOf(AuthorizedException.class);
        verify(accountRt, times(1)).validateMemberExists(anyInt());
        assertThat(project.getStatus()).isNotEqualTo(ProjectStatus.DORMANT);
    }

    @Test
    void 프로젝트에_멤버를_등록한다() throws NoSuchFieldException, IllegalAccessException {
        Project project = makeTestProject(1L, 1, null,
            null, ProjectStatus.ACTIVATION);
        MemberInProject memberInProject = makeTestMemberInProject(1L, project, 1);

        doNothing().when(accountRt).validateMemberExists(anyInt());
        when(projectRepository.findById(any())).thenReturn(Optional.of(project));
        when(memberInProjectRepository.existsByProject_idAndMemberId(any(), anyInt()))
            .thenReturn(false);
        when(memberInProjectRepository.save(any())).thenReturn(memberInProject);

        projectService.registerMemberInProject(1L, 1, 2);

        verify(accountRt, times(2)).validateMemberExists(anyInt());
        verify(memberInProjectRepository, times(1)).save(any());
    }

    @Test
    void 프로젝트에_멤버_등록할때_등록자가_존재하지_않으면_예외_반환한다() {
        doThrow(MemberNotFoundException.class).when(accountRt).validateMemberExists(1);

        assertThatThrownBy(() -> projectService.registerMemberInProject(1L, 1, 2))
            .isInstanceOf(MemberNotFoundException.class);
        verify(accountRt, times(1)).validateMemberExists(anyInt());
        verify(memberInProjectRepository, never()).save(any());
    }

    @Test
    void 프로젝트에_멤버_등록할때_종료된_프로젝트면_예외를_반환한다() throws NoSuchFieldException, IllegalAccessException {
        Project project = makeTestProject(1L, 1, null,
            null, ProjectStatus.EXIT);

        doNothing().when(accountRt).validateMemberExists(anyInt());
        when(projectRepository.findById(any())).thenReturn(Optional.of(project));

        assertThatThrownBy(() -> projectService.registerMemberInProject(1L, 1, 2))
            .isInstanceOf(ProjectExitException.class);

        verify(accountRt, times(2)).validateMemberExists(anyInt());
        verify(memberInProjectRepository, never()).save(any());
    }

    @Test
    void 프로젝트에_멤버_등록할떄_등록자가_해당_프로젝트의_어드민이_아니면_예외를_반환한다()
        throws NoSuchFieldException, IllegalAccessException {
        Project project = makeTestProject(1L, 1, null,
            null, ProjectStatus.ACTIVATION);
        doNothing().when(accountRt).validateMemberExists(anyInt());
        when(projectRepository.findById(any())).thenReturn(Optional.of(project));

        assertThatThrownBy(() ->
            projectService.registerMemberInProject(1L, 100, 2))
            .isInstanceOf(AuthorizedException.class);
        verify(accountRt, times(2)).validateMemberExists(anyInt());
        verify(memberInProjectRepository, never()).save(any());
    }

    @Test
    void 프로젝트에_멤버_등록할때_이미_등록된_멤버인경우_예외_반환한다() throws NoSuchFieldException, IllegalAccessException {
        Project project = makeTestProject(1L, 1, null,
            null, ProjectStatus.ACTIVATION);

        doNothing().when(accountRt).validateMemberExists(anyInt());
        when(projectRepository.findById(any())).thenReturn(Optional.of(project));
        when(memberInProjectRepository.existsByProject_idAndMemberId(any(), anyInt()))
            .thenReturn(true);

        assertThatThrownBy(() -> projectService.registerMemberInProject(1L, 1, 2))
            .isInstanceOf(AlreadyProjectMemberException.class);
        verify(accountRt, times(2)).validateMemberExists(anyInt());
        verify(memberInProjectRepository, never()).save(any());
    }



    private MemberInProject makeTestMemberInProject(Long id, Project project, Integer memberId)
        throws NoSuchFieldException, IllegalAccessException {
        MemberInProject memberInProject = MemberInProject.make(project, memberId);

        Class<MemberInProject> memberInProjectClazz = MemberInProject.class;
        Field idField = memberInProjectClazz.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(memberInProject, id);

        return memberInProject;
    }

    private static ProjectDetail makeTestProjectDetail(Long id, int adminId, String name,
                                                       String description, ProjectStatus status) {
        ProjectDetail projectDetail = new ProjectDetail() {
            @Override
            public Long getId() {
                return id;
            }

            @Override
            public int getAdminId() {
                return adminId;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public ProjectStatus getStatus() {
                return status;
            }
        };
        return projectDetail;
    }

    private static Project makeTestProject(Long id, int adminId, String name, String description,
                                           ProjectStatus status)
        throws NoSuchFieldException, IllegalAccessException {
        Project project = Project.make(adminId, name, description);
        Class<Project> projectClazz = Project.class;
        Field idField = projectClazz.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(project, id);

        Field statusField = projectClazz.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(project, status);

        return project;
    }
}