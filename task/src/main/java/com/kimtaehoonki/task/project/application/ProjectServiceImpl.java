package com.kimtaehoonki.task.project.application;

import com.kimtaehoonki.task.ProjectStatus;
import com.kimtaehoonki.task.exception.impl.AuthorizedException;
import com.kimtaehoonki.task.exception.impl.ProjectExitException;
import com.kimtaehoonki.task.exception.impl.ProjectNameDuplicateException;
import com.kimtaehoonki.task.exception.impl.ProjectNotFoundException;
import com.kimtaehoonki.task.project.domain.ProjectRepository;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.project.presentation.dto.CreateProjectRequestDto;
import com.kimtaehoonki.task.project.presentation.dto.ShowProjectResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

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

    @Override
    public List<String> showProjectsNameBelongsToMember(Integer userId) {
        return null;
    }

    @Override
    public ShowProjectResponseDto showProject(Long projectId, Integer memberId) {
        return null;
    }

    /**
     * 해당 유저가 존재하는지, 어드민이 맞는지 확인한다
     * 프로젝트의 status가 종료인 경우 예외를 반환한다
     * 위 조건을 모두 만족하면 해당 Project의 상태를 변경한다
     */
    @Transactional
    @Override
    public void updateProjectStatus(Long projectId, int adminId, ProjectStatus status) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(ProjectNotFoundException::new);
        if (project.isExit()) {
            throw new ProjectExitException();
        }
        boolean isAdmin = project.checkAdmin(adminId);
        if (!isAdmin) {
            throw new AuthorizedException();
        }
        project.changeStatus(status);
    }

    @Override
    public void registerUserInProject(Long projectId, Integer registerId, Integer targetId) {

    }
}
