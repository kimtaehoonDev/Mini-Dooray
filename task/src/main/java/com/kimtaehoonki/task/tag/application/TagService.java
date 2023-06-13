package com.kimtaehoonki.task.tag.application;

import com.kimtaehoonki.task.project.presentation.dto.response.GetTagsByProjectIdResponseDto;

public interface TagService {

    Long registerTag(String name, Long projectId, Integer memberId);

    void deleteTag(Long tagId, Integer memberId);

    GetTagsByProjectIdResponseDto getTagsByProjectId(Long projectId);
}
