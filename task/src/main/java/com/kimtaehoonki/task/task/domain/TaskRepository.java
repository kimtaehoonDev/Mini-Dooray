package com.kimtaehoonki.task.task.domain;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Integer countByProjectId(Long projectId);

    List<Task> findAllByProjectId(Long projectId, Pageable pageable);

}
