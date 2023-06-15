package com.kimtaehoonki.task.tagtask;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagTaskRepository extends JpaRepository<TagTask, Long> {
    List<TagTask> findAllByTaskId(Long taskId);

    void deleteAllByTask_id(Long taskId);
}
