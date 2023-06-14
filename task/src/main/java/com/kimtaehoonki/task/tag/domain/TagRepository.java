package com.kimtaehoonki.task.tag.domain;

import com.kimtaehoonki.task.tag.dto.TagResponseDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<TagResponseDto> findAllByProject_id(Long projectId);
}
