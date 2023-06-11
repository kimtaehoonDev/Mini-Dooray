package com.kimtaehoonki.task.comment.application;

import com.kimtaehoonki.task.comment.application.dto.response.CommentResponseDto;
import java.util.List;

public interface CommentService {
    Long comment(String contents, Integer memberId);

    List<CommentResponseDto> getCommentsInTask(Long taskId);

    void updateComment(Long commentId, String contents, Integer memberId);

    void deleteComment(Long commentId, Integer memberId);
}
