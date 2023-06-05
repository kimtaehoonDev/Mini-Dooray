package com.kimtaehoonki.task.tag.domain;

import com.kimtaehoonki.task.project.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
