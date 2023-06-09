package com.kimtaehoonki.task.comment.application;

import com.kimtaehoonki.task.comment.dto.response.CommentResponseDto;
import java.util.List;

public interface CommentService {
    Long comment(String contents, Integer memberId, Long taskId);

    List<CommentResponseDto> getCommentsInTask(Long taskId);

    void updateComment(Long commentId, String contents, Integer writerId);

    void deleteComment(Long commentId, Integer writerId);
}
