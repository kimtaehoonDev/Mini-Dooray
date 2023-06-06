package com.kimtaehoonki.task.project.application.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ProjectPreview {
    private Long projectId;
    private String title;

    @QueryProjection

    public ProjectPreview(Long projectId, String title) {
        this.projectId = projectId;
        this.title = title;
    }
}
