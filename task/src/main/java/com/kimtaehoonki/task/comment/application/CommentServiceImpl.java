package com.kimtaehoonki.task.comment.application;

import com.kimtaehoonki.task.comment.application.dto.response.CommentResponseDto;
import com.kimtaehoonki.task.comment.domain.Comment;
import com.kimtaehoonki.task.comment.domain.CommentRepository;
import com.kimtaehoonki.task.member.AccountRestTemplate;
import com.kimtaehoonki.task.member.MemberResponseDto;
import com.kimtaehoonki.task.task.domain.Task;
import com.kimtaehoonki.task.task.domain.TaskRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final AccountRestTemplate accountRt;

    @Override
    public Long comment(String contents, Integer memberId, Long taskId) {
        MemberResponseDto memberInfo = accountRt.getMemberInfo(memberId);

        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("TODO.. Task가 존재하지 않습니다"));

        Comment savedComment = commentRepository.save(Comment.create(task, memberId,
            memberInfo.getName(), contents));
        return savedComment.getId();
    }

    @Override
    public List<CommentResponseDto> getCommentsInTask(Long taskId) {
        return commentRepository.findAllByTask_id(taskId);
    }

    @Override
    public void updateComment(Long commentId, String contents, Integer writerId) {
        // 권한을 확인한다
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("TODO .. Comment가 존재하지 않습니다"));
        if (!comment.isWritten(writerId)) {
            throw new IllegalArgumentException("TODO... Comment 작성자 아닙니다");
        }
        comment.setContents(contents);
    }

    @Override
    public void deleteComment(Long commentId, Integer memberId) {
        // 권한을 확인한다

    }
}
