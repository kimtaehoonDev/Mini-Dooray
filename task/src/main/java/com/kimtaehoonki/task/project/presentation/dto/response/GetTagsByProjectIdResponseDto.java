package com.kimtaehoonki.task.project.presentation.dto.response;

import com.kimtaehoonki.task.tag.application.dto.TagResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetTagsByProjectIdResponseDto {
    List<TagResponseDto> tags;
}
