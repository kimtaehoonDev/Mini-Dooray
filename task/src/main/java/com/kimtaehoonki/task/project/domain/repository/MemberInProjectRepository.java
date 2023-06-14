package com.kimtaehoonki.task.project.domain.repository;

import com.kimtaehoonki.task.project.domain.entity.MemberInProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInProjectRepository extends JpaRepository<MemberInProject, Long>, MemberInProjectRepositoryCustom {
    boolean existsByProject_idAndMemberId(Long projectId, Integer memberId);
}
