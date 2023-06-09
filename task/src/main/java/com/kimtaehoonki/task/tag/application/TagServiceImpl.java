package com.kimtaehoonki.task.tag.application;

import com.kimtaehoonki.task.exception.impl.ResourceNameDuplicatedException;
import com.kimtaehoonki.task.tag.ColorCode;
import com.kimtaehoonki.task.exception.impl.AuthorizedException;
import com.kimtaehoonki.task.exception.impl.ProjectNotFoundException;
import com.kimtaehoonki.task.exception.impl.TagNotFoundException;
import com.kimtaehoonki.task.member.AccountRestTemplate;
import com.kimtaehoonki.task.project.domain.repository.MemberInProjectRepository;
import com.kimtaehoonki.task.project.domain.repository.ProjectRepository;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.project.presentation.dto.response.GetTagsByProjectIdResponseDto;
import com.kimtaehoonki.task.tag.dto.TagResponseDto;
import com.kimtaehoonki.task.tag.domain.Tag;
import com.kimtaehoonki.task.tag.domain.TagRepository;
import com.kimtaehoonki.task.utils.ColorGenerator;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;
    private final MemberInProjectRepository memberInProjectRepository;
    private final AccountRestTemplate accountRt;
    private final Random random;

    @Override
    @Transactional
    public Long registerTag(String name, Long projectId, Integer memberId) {
        accountRt.validateMemberExists(memberId);

        Project project = projectRepository.findById(projectId)
            .orElseThrow(ProjectNotFoundException::new);

        boolean isMemberInProject =
            memberInProjectRepository.existsByProject_idAndMemberId(projectId, memberId);
        if (!isMemberInProject) {
            throw new AuthorizedException();
        }

        boolean isDuplicatedName = tagRepository.existsByNameAndProject_id(name, projectId);
        if (isDuplicatedName) {
            throw new ResourceNameDuplicatedException();
        }
        ColorCode colorCode = ColorGenerator.generate(random);
        Tag tag = Tag.create(project, name, colorCode);
        Tag savedTag = tagRepository.save(tag);
        return savedTag.getId();
    }

    @Override
    @Transactional
    public void deleteTag(Long tagId, Integer memberId) {
        accountRt.validateMemberExists(memberId);

        Tag tag = tagRepository.findById(tagId)
            .orElseThrow(TagNotFoundException::new);

        boolean isMemberInProject =
            memberInProjectRepository.existsByProject_idAndMemberId(tag.getProject().getId(), memberId);
        if (!isMemberInProject) {
            throw new AuthorizedException();
        }

        tagRepository.delete(tag);
    }

    @Override
    public GetTagsByProjectIdResponseDto getTagsByProjectId(Long projectId) {
        List<TagResponseDto> tags = tagRepository.findAllByProject_id(projectId);
        return new GetTagsByProjectIdResponseDto(tags);
    }
}
