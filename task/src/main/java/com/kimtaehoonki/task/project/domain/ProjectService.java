package com.kimtaehoonki.task.project.domain;

import com.kimtaehoonki.task.project.presentation.dto.CreateProjectRequestDto;

public interface ProjectService {
    /**
     * 생성된 프로젝트의 ID를 반환한다
     * 만약 프로젝트의 이름이 이미 존재하는 경우, 예외를 반환한다
     */
    long createProject(CreateProjectRequestDto dto);
}
