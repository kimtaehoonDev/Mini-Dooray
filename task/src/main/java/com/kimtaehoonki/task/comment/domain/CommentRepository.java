package com.kimtaehoonki.task.comment.domain;

import com.kimtaehoonki.task.project.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
