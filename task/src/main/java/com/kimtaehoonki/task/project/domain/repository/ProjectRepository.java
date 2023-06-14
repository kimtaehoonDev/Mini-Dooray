package com.kimtaehoonki.task.project.domain.repository;

import com.kimtaehoonki.task.project.application.dto.response.ProjectDetail;
import com.kimtaehoonki.task.project.domain.entity.Project;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByName(String name);

    <T> Optional<T> findById(Long aLong, Class<T> type);
}
