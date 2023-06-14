package com.kimtaehoonki.task.comment.domain;

import com.kimtaehoonki.task.comment.dto.response.CommentResponseDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<CommentResponseDto> findAllByTask_id(Long taskId);
}
