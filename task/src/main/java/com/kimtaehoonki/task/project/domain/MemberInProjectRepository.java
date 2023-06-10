package com.kimtaehoonki.task.project.domain;

import com.kimtaehoonki.task.project.domain.entity.MemberInProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInProjectRepository extends JpaRepository<MemberInProject, Long> {
    boolean existsByProject_idAndMemberId(Long projectId, Integer registerId);
}
