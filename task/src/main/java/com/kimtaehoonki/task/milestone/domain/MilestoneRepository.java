package com.kimtaehoonki.task.milestone.domain;

import com.kimtaehoonki.task.milestone.dto.MilestoneResponseDto;
import com.kimtaehoonki.task.project.domain.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    List<MilestoneResponseDto> findAllByProject_id(Long projectId);

    boolean existsByName(String name);
}
