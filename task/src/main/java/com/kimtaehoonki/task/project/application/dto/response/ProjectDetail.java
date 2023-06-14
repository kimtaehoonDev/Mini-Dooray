package com.kimtaehoonki.task.project.application.dto.response;

import com.kimtaehoonki.task.project.ProjectStatus;

public interface ProjectDetail {
    Long getId();
    int getAdminId();
    String getName();
    String getDescription();
    ProjectStatus getStatus();
}
