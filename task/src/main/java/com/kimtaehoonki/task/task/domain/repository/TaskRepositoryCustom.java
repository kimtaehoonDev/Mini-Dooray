package com.kimtaehoonki.task.task.domain.repository;

import com.kimtaehoonki.task.task.application.dto.TaskPreview;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TaskRepositoryCustom {
    List<TaskPreview> getTasksPreview(Long projectId, Pageable pageable);
}
