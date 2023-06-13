package com.kimtaehoonki.task.tag.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kimtaehoonki.task.colorcode.ColorCode;
import com.kimtaehoonki.task.exception.impl.ProjectNotFoundException;
import com.kimtaehoonki.task.exception.impl.TagNotFoundException;
import com.kimtaehoonki.task.project.domain.ProjectRepository;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.tag.domain.Tag;
import com.kimtaehoonki.task.tag.domain.TagRepository;
import com.kimtaehoonki.task.utils.ColorGenerator;
import java.lang.reflect.Field;
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
    ColorGenerator colorGenerator;

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

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(colorGenerator.get()).thenReturn(ColorCode.BLUE);
        when(tagRepository.save(any())).thenReturn(tag);

        Long result = tagService.registerTag("hard", 1L);

        Assertions.assertThat(result).isEqualTo(10L);
        verify(projectRepository, times(1)).findById(any());
        verify(colorGenerator, times(1)).get();
        verify(tagRepository, times(1)).save(any());
    }

    @Test
    void 태그를_등록할때_프로젝트가_없으면_예외를_반환한다() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
            tagService.registerTag("hard", 1L))
            .isInstanceOf(ProjectNotFoundException.class);
        verify(projectRepository, times(1)).findById(any());
        verify(colorGenerator, never()).get();
        verify(tagRepository, never()).save(any());
    }

    @Test
    void 태그를_삭제한다() throws NoSuchFieldException, IllegalAccessException {
        Tag tag = Tag.create(null, "dsds", ColorCode.BLUE);

        when(tagRepository.findById(any())).thenReturn(Optional.of(tag));
        tagService.deleteTag(10L);

        verify(tagRepository, times(1)).findById(any());
        verify(tagRepository, times(1)).delete(any());
    }

    @Test
    void 태그를_삭제할때_해당_태그가_없으면_예외를_반환한다() {
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
            tagService.deleteTag(1L))
            .isInstanceOf(TagNotFoundException.class);
        verify(tagRepository, times(1)).findById(any());
        verify(tagRepository, never()).delete(any());
    }

}