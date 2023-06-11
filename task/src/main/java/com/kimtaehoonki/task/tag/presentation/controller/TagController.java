package com.kimtaehoonki.task.tag.presentation.controller;

import com.kimtaehoonki.task.tag.application.TagService;
import com.kimtaehoonki.task.tag.presentation.dto.DeleteTagResponseDto;
import com.kimtaehoonki.task.tag.presentation.dto.RegisterTagRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;


    /**
     * 태그를 등록한다.
     *
     */
    @PostMapping("/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerTag(@RequestBody RegisterTagRequestDto dto) {
        String name = dto.getName();
        tagService.registerTag(name);
    }

    /**
     * 해당 태그를 삭제한다.
     *
     */
    @DeleteMapping("/tags/{id}")
    public void deleteTag(@PathVariable("id") Long tagId) {
        tagService.deleteTag(tagId);
    }
}
