package com.kimtaehoonki.task.comment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kimtaehoonki.task.comment.application.dto.response.CommentResponseDto;
import com.kimtaehoonki.task.comment.domain.Comment;
import com.kimtaehoonki.task.comment.domain.CommentRepository;
import com.kimtaehoonki.task.exception.impl.AuthorizedException;
import com.kimtaehoonki.task.exception.impl.CommentNotFoundException;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AccountRestTemplate accountRt;

    @InjectMocks
    CommentServiceImpl commentService;

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
        verify(commentRepository, times(1)).findAllByTask_id(any());
    }

    @Test
    void 댓글을_업데이트한다() throws NoSuchFieldException, IllegalAccessException {
        int writerId = 1;
        Comment comment = makeTestComment(1L, null, writerId, null, "ㅇ");
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        commentService.updateComment(1L, "변경내용", writerId);

        verify(commentRepository, times(1)).findById(1L);
        assertThat(comment.getContents()).isEqualTo("변경내용");
    }

    @Test
    void 댓글수정시_댓글이_존재하지_않으면_예외를_반환한다() {
        when(commentRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
            commentService.updateComment(1L, "변경내용", 1))
            .isInstanceOf(CommentNotFoundException.class);
        verify(commentRepository, times(1)).findById(any());
    }

    @Test
    void 댓글을_작성하지_않은_사람이_업데이트를_시도하면_예외를_반환한다() throws NoSuchFieldException, IllegalAccessException {
        int writerId = 1;
        Comment comment = makeTestComment(1L, null, writerId, null, "ㅇ");
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        assertThatThrownBy(() ->
            commentService.updateComment(1L, "변경내용", writerId + 1))
            .isInstanceOf(AuthorizedException.class);

        verify(commentRepository, times(1)).findById(any());
    }

    @Test
    void 댓글을_삭제한다() throws NoSuchFieldException, IllegalAccessException {
        int writerId = 1;
        Comment comment = makeTestComment(1L, null, writerId, null, "ㅇ");
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        commentService.deleteComment(1L, writerId);

        verify(commentRepository, times(1)).findById(1L);
        verify(commentRepository, times(1)).delete(any());
    }

    @Test
    void 댓글삭제시_댓글이_존재하지_않으면_예외를_반환한다() {
        when(commentRepository.findById(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
            commentService.deleteComment(1L, 1))
            .isInstanceOf(CommentNotFoundException.class);
        verify(commentRepository, times(1)).findById(any());
        verify(commentRepository, never()).delete(any());

    }

    @Test
    void 댓글을_작성하지_않은_사람이_삭제를_시도하면_예외를_반환한다() throws NoSuchFieldException, IllegalAccessException {
        int writerId = 1;
        Comment comment = makeTestComment(1L, null, writerId, null, "ㅇ");
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        assertThatThrownBy(() ->
            commentService.deleteComment(1L, writerId + 1))
            .isInstanceOf(AuthorizedException.class);

        verify(commentRepository, times(1)).findById(any());
        verify(commentRepository, never()).delete(any());
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