package com.kimtaehoonki.task.tag.dto;

import com.kimtaehoonki.task.tag.ColorCode;
import com.kimtaehoonki.task.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TagResponseDto {
    private Long id;
    private String name;
    private ColorCode colorCode;

    public static TagResponseDto from(Tag tag) {
        return new TagResponseDto(tag.getId(), tag.getName(), tag.getColorCode());
    }
}
