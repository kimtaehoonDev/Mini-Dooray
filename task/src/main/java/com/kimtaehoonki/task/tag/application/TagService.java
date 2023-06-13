package com.kimtaehoonki.task.tag.application;

import com.kimtaehoonki.task.project.presentation.dto.response.GetTagsByProjectIdResponseDto;

public interface TagService {

    Long registerTag(String name, Long projectId);

    void deleteTag(Long tagId);

    GetTagsByProjectIdResponseDto getTagsByProjectId(Long projectId);
}
