package com.kimtaehoonki.task.project.presentation.controller;

import com.kimtaehoonki.task.ProjectStatus;
import com.kimtaehoonki.task.project.application.ProjectService;
import com.kimtaehoonki.task.project.application.dto.response.ProjectDetail;
import com.kimtaehoonki.task.project.application.dto.response.ProjectPreview;
import com.kimtaehoonki.task.project.presentation.dto.request.CreateProjectRequestDto;
import com.kimtaehoonki.task.project.presentation.dto.response.CreateProjectResponseDto;
import com.kimtaehoonki.task.project.presentation.dto.GetMilestonesByProjectId;
import com.kimtaehoonki.task.project.presentation.dto.response.GetTagsByProjectIdResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProjectController.
 */
@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    /**
     * 프로젝트를 만든다.
     * 프로젝트 이름이 중복되는 경우 예외를 반환한다
     *
     * @param dto CreateProjectRequestDto
     * @return CreateProjectResponseDto
     */
    @PostMapping("/projects")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProjectResponseDto createProject(
        @RequestBody CreateProjectRequestDto dto) {
        long projectId = projectService.createProject(dto);
        return new CreateProjectResponseDto(projectId);
    }

    /**
     * 각 사용자마다 속해있는 프로젝트 목록을 보여준다.
     *
     * @param memberId @CookieValue
     * @return List.GetProjectResponseDto
     */
    @GetMapping("/projects")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectPreview> showProjectsNameBelongsToMember(@CookieValue Integer memberId) {
        return projectService.showProjectsPreviewsBelongsToMember(memberId);
    }

    /**
     * 선택한 프로젝트에 대해 보여준다.
     *
     * @param projectId @PathVariable
     * @return GetProjectResponseDto
     */
    @GetMapping("/projects/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDetail showProject(@PathVariable("id") Long projectId,
                                     @CookieValue Integer memberId) {
        return projectService.showProject(projectId, memberId);
    }

    /**
     * 프로젝트 상태변경을 한다.
     * 프로젝트 삭제는 제공되지 않는다
     * 대신 상태변경을 통해 이루어진다
     *
     * @param projectId @PathVariable
     * @return UpdateProjectStatusResponseDto
     */
    @PutMapping("/projects/{id}/status")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateProjectStatus(@PathVariable("id") Long projectId,
                                    @CookieValue Integer memberId,
                                    @RequestParam ProjectStatus status) {
        projectService.updateProjectStatus(projectId, memberId, status);
    }

    /**
     * 프로젝트에 사용자를 등록한다.
     */
    @PostMapping("/projects/{id}/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUserInProject(@PathVariable("id") Long projectId,
                                      @CookieValue Integer memberId,
                                      @RequestParam("targetId") Integer targetId) {
        projectService.registerMemberInProject(projectId, memberId, targetId);
    }

    /**
     * 특정 프로젝트의 모든 태그들을 조회한다.
     *
     * @param projectId @PathVariable
     * @return GetTagsByProjectIdResponseDto
     */
    @GetMapping("/projects/{id}/tags")
    public GetTagsByProjectIdResponseDto getTagsByProject(@PathVariable("id") Long projectId) {
        // TODO 태그 관련한 패키지가 만들어진 이후 작업할 예정입니다
        return null;
    }

    /**
     * 특정 프로젝트의 마일스톤을 조회한다.
     *
     * @param projectId @PathVariable
     * @return GetMilestonesByProjectId
     */
    @GetMapping("/projects/{id}/milestones")
    public GetMilestonesByProjectId getMilestonesByProjectId(@PathVariable("id") Long projectId) {
        // TODO 마일스톤 관련한 패키지가 만들어진 이후 작업할 예정입니다
        return null;
    }
}
