package com.kimtaehoonki.task.milestone.domain;

import com.kimtaehoonki.task.project.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
}
