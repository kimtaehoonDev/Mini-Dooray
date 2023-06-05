package com.kimtaehoonki.task.project.domain;

import com.kimtaehoonki.task.project.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
