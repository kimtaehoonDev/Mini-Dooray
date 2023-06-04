package com.kimtaehoonki.task.presentation.controller;

import com.kimtaehoonki.task.domain.entity.Project;
import com.kimtaehoonki.task.presentation.dto.CreateProjectRequestDto;
import com.kimtaehoonki.task.presentation.dto.CreateProjectResponseDto;
import com.kimtaehoonki.task.presentation.dto.GetMilestonesByProjectId;
import com.kimtaehoonki.task.presentation.dto.GetProjectResponseDto;
import com.kimtaehoonki.task.presentation.dto.GetTagsByProjectIdResponseDto;
import com.kimtaehoonki.task.presentation.dto.RegisterUserResponseDto;
import com.kimtaehoonki.task.presentation.dto.UpdateProjectStatusResponseDto;
import java.util.List;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProjectController.
 */
@RestController
public class ProjectController {
    /**
     * 프로젝트를 만든다.
     *
     * @param createProjectRequestDto CreateProjectRequestDto
     * @return CreateProjectResponseDto
     */
    @PostMapping("/projects")
    public CreateProjectResponseDto createProject(
            @RequestBody CreateProjectRequestDto createProjectRequestDto) {
        return null;
    }

    /**
     * 프로젝트 목록을 확인한다.
     * 사용자마다 보여주는게 다르다
     * 아이디는 쿠키로 넘어온다
     *
     * @param userId @CookieValue
     * @return List.GetProjectResponseDto
     */
    @GetMapping("/projects")
    public List<GetProjectResponseDto> getProjects(@CookieValue String userId) {
        return null;
    }

    /**
     * 특정 프로젝트에 들어간다.
     *
     * @param projectId @PathVariable
     * @return GetProjectResponseDto
     */
    @GetMapping("/projects/{id}")
    public GetProjectResponseDto getProject(@PathVariable("id") Long projectId) {
        return null;
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
    public UpdateProjectStatusResponseDto updateProjectStatus(@PathVariable("id") Long projectId) {
        return null;
    }

    /**
     * 프로젝트에 특정 유저를 등록한다.
     *
     * @param projectId @PathVariable
     * @param userId @RequestParam
     * @return RegisterUserResponseDto
     */
    @PostMapping("/projects/{id}/register")
    public RegisterUserResponseDto registerUser(@PathVariable("id") Long projectId,
                                                @RequestParam("userId") Integer userId) {

        return null;
    }

    /**
     * 특정 프로젝트의 모든 태그들을 조회한다.
     *
     * @param projectId @PathVariable
     * @return GetTagsByProjectIdResponseDto
     */
    @GetMapping("/projects/{id}/tags")
    public GetTagsByProjectIdResponseDto getTagsByProject(@PathVariable("id") Long projectId) {
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
        return null;
    }
}
