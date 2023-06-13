package com.kimtaehoonki.task.tag.application;

import com.kimtaehoonki.task.colorcode.ColorCode;
import com.kimtaehoonki.task.exception.impl.ProjectNotFoundException;
import com.kimtaehoonki.task.exception.impl.TagNotFoundException;
import com.kimtaehoonki.task.project.domain.ProjectRepository;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.project.presentation.dto.response.GetTagsByProjectIdResponseDto;
import com.kimtaehoonki.task.tag.application.dto.TagResponseDto;
import com.kimtaehoonki.task.tag.domain.Tag;
import com.kimtaehoonki.task.tag.domain.TagRepository;
import com.kimtaehoonki.task.utils.ColorGenerator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;
    private final ColorGenerator colorGenerator;

    @Override
    @Transactional
    public Long registerTag(String name, Long projectId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(ProjectNotFoundException::new);

        ColorCode colorCode = colorGenerator.get();
        Tag tag = Tag.create(project, name, colorCode);
        Tag savedTag = tagRepository.save(tag);
        return savedTag.getId();
    }

    @Override
    @Transactional
    public void deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
            .orElseThrow(TagNotFoundException::new);
        tagRepository.delete(tag);
    }

    @Override
    public GetTagsByProjectIdResponseDto getTagsByProjectId(Long projectId) {
        List<TagResponseDto> tags = tagRepository.findAllByProject_id(projectId);
        return new GetTagsByProjectIdResponseDto(tags);
    }
}
