package com.kimtaehoonki.task.tag.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kimtaehoonki.task.colorcode.ColorCode;
import com.kimtaehoonki.task.exception.impl.AuthorizedException;
import com.kimtaehoonki.task.exception.impl.ProjectNotFoundException;
import com.kimtaehoonki.task.exception.impl.TagNotFoundException;
import com.kimtaehoonki.task.member.AccountRestTemplate;
import com.kimtaehoonki.task.project.domain.MemberInProjectRepository;
import com.kimtaehoonki.task.project.domain.ProjectRepository;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.project.presentation.dto.response.GetTagsByProjectIdResponseDto;
import com.kimtaehoonki.task.tag.application.dto.TagResponseDto;
import com.kimtaehoonki.task.tag.domain.Tag;
import com.kimtaehoonki.task.tag.domain.TagRepository;
import com.kimtaehoonki.task.utils.ColorGenerator;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    TagRepository tagRepository;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    MemberInProjectRepository memberInProjectRepository;

    @Mock
    AccountRestTemplate accountRt;

    @InjectMocks
    TagServiceImpl tagService;

    @Test
    void 태그를_등록한다() throws NoSuchFieldException, IllegalAccessException {
        Project project = Project.make(1, null, null);
        Tag tag = Tag.create(project, "dsds", ColorCode.BLUE);

        Class<Tag> tagClazz = Tag.class;
        Field idField = tagClazz.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(tag, 10L);

        doNothing().when(accountRt).validateMemberExists(anyInt());

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(tagRepository.save(any())).thenReturn(tag);
        when(memberInProjectRepository.existsByProject_idAndMemberId(any(), anyInt()))
            .thenReturn(true);

        Long result = tagService.registerTag("hard", 1L, 1);

        Assertions.assertThat(result).isEqualTo(10L);
        verify(projectRepository, times(1)).findById(any());
        verify(tagRepository, times(1)).save(any());
    }

    @Test
    void 태그를_등록할때_프로젝트가_없으면_예외를_반환한다() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
            tagService.registerTag("hard", 1L, 1))
            .isInstanceOf(ProjectNotFoundException.class);

        verify(projectRepository, times(1)).findById(any());
        verify(tagRepository, never()).save(any());
    }

    @Test
    void 태그를_등록할때_멤버가_프로젝트에_포함되지_않았다면_예외를_반환한다() {
        Project project = Project.make(1, null, null);
        doNothing().when(accountRt).validateMemberExists(anyInt());

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(memberInProjectRepository.existsByProject_idAndMemberId(any(), anyInt()))
            .thenReturn(false);
        assertThatThrownBy(() ->
            tagService.registerTag("hard", 1L, 1))
            .isInstanceOf(AuthorizedException.class);

        verify(projectRepository, times(1)).findById(any());
        verify(tagRepository, never()).save(any());
    }

    @Test
    void 태그를_삭제한다() {
        Tag tag = Tag.create(Project.make(1, null, null),
            "dsds", ColorCode.BLUE);

        when(tagRepository.findById(any())).thenReturn(Optional.of(tag));
        when(memberInProjectRepository.existsByProject_idAndMemberId(any(), anyInt()))
            .thenReturn(true);
        tagService.deleteTag(10L, 1);

        verify(tagRepository, times(1)).findById(any());
        verify(tagRepository, times(1)).delete(any());
    }

    @Test
    void 태그를_삭제할때_해당_태그가_없으면_예외를_반환한다() {
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
            tagService.deleteTag(1L, 1))
            .isInstanceOf(TagNotFoundException.class);
        verify(tagRepository, times(1)).findById(any());
        verify(tagRepository, never()).delete(any());
    }

    @Test
    void 프로젝트_아이디로_태그목록을_조회한다() {
        Tag tag = Tag.create(null, "tag1", ColorCode.GREEN);
        TagResponseDto tagResponseDto = TagResponseDto.from(tag);
        when(tagRepository.findAllByProject_id(any()))
            .thenReturn(List.of(tagResponseDto));

        GetTagsByProjectIdResponseDto result = tagService.getTagsByProjectId(1L);
        assertThat(result.getTags()).contains(tagResponseDto);
        assertThat(result.getTags()).hasSize(1);

    }
}