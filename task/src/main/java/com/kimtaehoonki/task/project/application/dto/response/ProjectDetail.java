package com.kimtaehoonki.task.project.application.dto.response;

import com.kimtaehoonki.task.ProjectStatus;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;

@Getter
public class ProjectDetail {
    private Long id;
    private int adminId;
    private String name;
    private String description;
    private ProjectStatus status;

    public boolean isExit() {
        return status.isExit();
    }
}
