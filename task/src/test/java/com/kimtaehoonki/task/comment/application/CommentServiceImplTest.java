package com.kimtaehoonki.task.comment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kimtaehoonki.task.comment.application.dto.response.CommentResponseDto;
import com.kimtaehoonki.task.comment.domain.Comment;
import com.kimtaehoonki.task.comment.domain.CommentRepository;
import com.kimtaehoonki.task.exception.impl.MemberNotFoundException;
import com.kimtaehoonki.task.exception.impl.TaskNotFoundException;
import com.kimtaehoonki.task.member.AccountRestTemplate;
import com.kimtaehoonki.task.member.MemberResponseDto;
import com.kimtaehoonki.task.task.domain.Task;
import com.kimtaehoonki.task.task.domain.TaskRepository;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;


@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AccountRestTemplate accountRt;

    CommentServiceImpl commentService;

    @BeforeEach
    void beforeEach() {
        commentService = new CommentServiceImpl(commentRepository, taskRepository, accountRt);

    }

    @Test
    void 댓글을_작성한다() throws NoSuchFieldException, IllegalAccessException {
        MemberResponseDto memberInfo = new MemberResponseDto();
        memberInfo.setName("kim");

        Task task = new Task();
        Comment comment = makeTestComment(100L, task, 1, "작성자", "내용");

        when(accountRt.getMemberInfo(any()))
            .thenReturn(memberInfo);
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        when(commentRepository.save(any())).thenReturn(comment);

        Long commentId = commentService.comment("댓글내용", 1, 1L);

        Assertions.assertThat(commentId).isEqualTo(100L);
        verify(commentRepository, times(1)).save(any());
    }

    @Test
    void 댓글_작성시_존재하지_않는_유저가_입력되면_예외를_반환한다() {
        doThrow(MemberNotFoundException.class).when(accountRt).getMemberInfo(any());

        assertThatThrownBy(() -> commentService.comment("댓글내용", 1, 1L))
            .isInstanceOf(MemberNotFoundException.class);
        verify(accountRt, times(1)).getMemberInfo(any());
        verify(taskRepository, never()).findById(any());
        verify(commentRepository, never()).save(any());
    }

    @Test
    void 댓글_작성시_존재하지_않는_태스크가_입력되면_예외를_반환한다() {
        MemberResponseDto memberInfo = new MemberResponseDto();
        memberInfo.setName("kim");

        when(accountRt.getMemberInfo(any()))
            .thenReturn(memberInfo);
        when(taskRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.comment("댓글내용", 1, 1L))
            .isInstanceOf(TaskNotFoundException.class);
        verify(accountRt, times(1)).getMemberInfo(any());
        verify(taskRepository, times(1)).findById(any());
        verify(commentRepository, never()).save(any());
    }

    @Test
    void 특정_태스크의_댓글을_전부_가져온다() {
        List<CommentResponseDto> comments = new ArrayList<>();

        comments.add(makeTestCommentResponseDto(1L, null, null, null));
        comments.add(makeTestCommentResponseDto(2L, null, null, null));
        when(commentRepository.findAllByTask_id(10L)).thenReturn(comments);

        List<CommentResponseDto> result = commentService.getCommentsInTask(10L);
        assertThat(result).hasSize(2);
    }

    private CommentResponseDto makeTestCommentResponseDto(Long commentId, Integer writerId,
                                                          String writerName, String contents) {
        CommentResponseDto dto = new CommentResponseDto() {
            @Override
            public Long getId() {
                return commentId;
            }

            @Override
            public LocalDateTime getCreatedAt() {
                return LocalDateTime.now();
            }

            @Override
            public Integer getWriterId() {
                return writerId;
            }

            @Override
            public String getWriterName() {
                return writerName;
            }

            @Override
            public String getContents() {
                return contents;
            }
        };

        return dto;
    }

    private Comment makeTestComment(long commentId, Task task, int writerId, String writerName, String contents)
        throws NoSuchFieldException, IllegalAccessException {
        Comment comment = Comment.create(task, writerId, writerName, contents);

        Class<Comment> commentClazz = Comment.class;
        Field idField = commentClazz.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(comment, commentId);

        return comment;
    }
}