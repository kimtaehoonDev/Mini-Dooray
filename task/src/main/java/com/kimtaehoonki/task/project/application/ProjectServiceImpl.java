package com.kimtaehoonki.task.project.application;

import com.kimtaehoonki.task.ProjectStatus;
import com.kimtaehoonki.task.exception.impl.ProjectNameDuplicateException;
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

    @Override
    public void updateProjectStatus(Long projectId, Integer memberId, ProjectStatus status) {

    }

    @Override
    public void registerUserInProject(Long projectId, Integer registerId, Integer targetId) {

    }
}
