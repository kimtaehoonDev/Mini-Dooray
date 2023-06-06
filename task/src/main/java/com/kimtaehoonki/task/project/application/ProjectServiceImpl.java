package com.kimtaehoonki.task.project.application;

import com.kimtaehoonki.task.ProjectStatus;
import com.kimtaehoonki.task.exception.impl.AlreadyProjectMemberException;
import com.kimtaehoonki.task.exception.impl.AuthenticationException;
import com.kimtaehoonki.task.exception.impl.AuthorizedException;
import com.kimtaehoonki.task.exception.impl.ProjectExitException;
import com.kimtaehoonki.task.exception.impl.ProjectNameDuplicateException;
import com.kimtaehoonki.task.exception.impl.ProjectNotFoundException;
import com.kimtaehoonki.task.project.application.dto.response.ProjectDetail;
import com.kimtaehoonki.task.project.application.dto.response.ProjectPreview;
import com.kimtaehoonki.task.project.domain.MemberInProjectQueryRepository;
import com.kimtaehoonki.task.project.domain.MemberInProjectRepository;
import com.kimtaehoonki.task.project.domain.ProjectRepository;
import com.kimtaehoonki.task.project.domain.entity.MemberInProject;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.project.presentation.dto.CreateProjectRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final MemberInProjectRepository memberInProjectRepository;
    private final MemberInProjectQueryRepository memberInProjectQueryRepository;

    /**
     * 생성된 프로젝트의 ID를 반환한다
     * 만약 프로젝트의 이름이 이미 존재하는 경우, 예외를 반환한다
     * 생성되는 프로젝트는 활성 상태이다
     */
    @Transactional
    @Override
    public long createProject(CreateProjectRequestDto dto) {
        boolean isDuplicatedName = projectRepository.existsByName(dto.getName());
        if (isDuplicatedName) {
            throw new ProjectNameDuplicateException();
        }

        Project project = Project.make(dto.getAdminId(), dto.getName(), dto.getDescription());
        Project saveProject = projectRepository.save(project);
        return saveProject.getId();
    }

    /**
     * 해당 유저가 속한 프로젝트의 목록을 보여준다
     * 프로젝트의 상태가 활성인 경우만 보여준다
     * userId가 존재하지 않으면 예외를 반환한다
     */
    @Override
    public List<ProjectPreview> showProjectsPreviewsBelongsToMember(Integer memberId) {
        return memberInProjectQueryRepository.findProjectsPreviewsUsingMemberId(memberId);
    }

    /**
     * 특정 프로젝트의 정보를 조회한다
     * 해당 유저가 해당 프로젝트에 속해있는지 확인한다
     * 만약, 조회한 프로젝트의 상태가 활성, 휴면이 아니라면 예외를 반환한다
     */
    @Override
    public ProjectDetail showProject(Long projectId, Integer memberId) {
        boolean isProjectMember =
            memberInProjectRepository.existsByProject_idAndMemberId(projectId, memberId);
        if (!isProjectMember) {
            throw new AuthenticationException();
        }
        ProjectDetail detail = projectRepository.findById(projectId, ProjectDetail.class)
            .orElseThrow(ProjectNotFoundException::new);
        if (detail.isExit()) {
            throw new ProjectExitException();
        }
        return detail;
    }

    /**
     * 해당 유저가 존재하는지, 어드민이 맞는지 확인한다
     * 프로젝트의 status가 종료인 경우 예외를 반환한다
     * 위 조건을 모두 만족하면 해당 Project의 상태를 변경한다
     */
    @Transactional
    @Override
    public void updateProjectStatus(Long projectId, int adminId, ProjectStatus status) {
        Project project = getProjectNotExit(projectId);
        boolean isAdmin = project.checkAdmin(adminId);
        if (!isAdmin) {
            throw new AuthorizedException();
        }
        project.changeStatus(status);
    }

    /**
     * 해당 유저가 존재하는지, 어드민이 맞는지 확인한다
     * 해당 유저가 존재하는지 확인한다
     * 프로젝트의 status가 종료인 경우 예외를 반환한다
     * 위 조건을 모두 만족하면 해당 Project의 상태를 변경한다
     */
    @Transactional
    @Override
    public Long registerMemberInProject(Long projectId, Integer registerId, Integer targetId) {
        Project project = getProjectNotExit(projectId);
        boolean isAdmin = project.checkAdmin(registerId);
        if (!isAdmin) {
            throw new AuthorizedException();
        }
        boolean isProjectsMember =
            memberInProjectRepository.existsByProject_idAndMemberId(projectId, targetId);
        if (isProjectsMember) {
            throw new AlreadyProjectMemberException();
        }
        MemberInProject memberInProject = MemberInProject.make(project, targetId);
        MemberInProject savedMemberInProject = memberInProjectRepository.save(memberInProject);
        return savedMemberInProject.getId();
    }

    /**
     * 프로젝트를 가져온다
     * 프로젝트의 상태가 Exit인 경우 예외를 반환한다
     */
    private Project getProjectNotExit(Long projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(ProjectNotFoundException::new);
        if (project.isExit()) {
            throw new ProjectExitException();
        }
        return project;
    }
}
