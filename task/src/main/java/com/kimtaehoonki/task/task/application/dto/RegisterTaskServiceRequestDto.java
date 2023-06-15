package com.kimtaehoonki.task.task.application.dto;

import com.kimtaehoonki.task.task.presentation.dto.RegisterTaskRequestDto;
import java.util.List;
import lombok.Getter;

@Getter
public class RegisterTaskServiceRequestDto {
    private Long projectId;
    private Long milestoneId;
    private String title;
    private String content;
    private List<Long> tagIds;
    private Integer memberId;

    public RegisterTaskServiceRequestDto(RegisterTaskRequestDto controllerRequestDto, Integer memberId) {
        this.projectId = controllerRequestDto.getProjectId();
        this.milestoneId = controllerRequestDto.getMilestoneId();
        this.title = controllerRequestDto.getTitle();
        this.content = controllerRequestDto.getContent();
        this.tagIds = controllerRequestDto.getTagIds();
        this.memberId = memberId;
    }
}
