package com.kimtaehoonki.task.task.domain;

import com.kimtaehoonki.task.project.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
