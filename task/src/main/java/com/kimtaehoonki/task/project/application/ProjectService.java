package com.kimtaehoonki.task.project.application;

import com.kimtaehoonki.task.ProjectStatus;
import com.kimtaehoonki.task.project.presentation.dto.CreateProjectRequestDto;
import com.kimtaehoonki.task.project.presentation.dto.ShowProjectResponseDto;
import java.util.List;

public interface ProjectService {
    /**
     * 생성된 프로젝트의 ID를 반환한다
     * 만약 프로젝트의 이름이 이미 존재하는 경우, 예외를 반환한다
     * 생성되는 프로젝트는 활성 상태이다
     */
    long createProject(CreateProjectRequestDto dto);

    /**
     * 해당 유저가 속한 프로젝트의 목록을 보여준다
     * 프로젝트의 상태가 활성인 경우만 보여준다
     * userId가 존재하지 않으면 예외를 반환한다
     */
    List<String> showProjectsNameBelongsToMember(Integer userId);


    /**
     * 특정 프로젝트의 정보를 조회한다
     * 해당 유저가 해당 프로젝트에 속해있는지 확인한다
     * 만약, 조회한 프로젝트의 상태가 활성, 휴면이 아니라면 예외를 반환한다
     */
    ShowProjectResponseDto showProject(Long projectId, Integer memberId);

    /**
     * 해당 유저가 존재하는지, 어드민이 맞는지 확인한다
     * 프로젝트의 status가 종료인 경우 예외를 반환한다
     * 위 조건을 모두 만족하면 해당 Project의 상태를 변경한다
     */
    void updateProjectStatus(Long projectId, int adminId, ProjectStatus status);

    /**
     * 해당 유저가 존재하는지, 어드민이 맞는지 확인한다
     * 해당 유저가 존재하는지 확인한다
     * 프로젝트의 status가 종료인 경우 예외를 반환한다
     * 위 조건을 모두 만족하면 해당 Project의 상태를 변경한다
     */
    Long registerMemberInProject(Long projectId, Integer registerId, Integer targetId);
}
