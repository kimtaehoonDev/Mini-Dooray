package com.kimtaehoonki.task.task.domain.repository;

import com.kimtaehoonki.task.task.domain.Task;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>, TaskRepositoryCustom {
    Integer countByProjectId(Long projectId);

    List<Task> findAllByProjectId(Long projectId, Pageable pageable);

}
