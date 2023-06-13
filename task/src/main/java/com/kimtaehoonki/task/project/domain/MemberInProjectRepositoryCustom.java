package com.kimtaehoonki.task.project.domain;

import com.kimtaehoonki.task.project.application.dto.response.ProjectPreview;
import java.util.List;

public interface MemberInProjectRepositoryCustom {
    List<ProjectPreview> findProjectsPreviewsUsingMemberId(Integer memberId);
}
