package com.kimtaehoonki.task.tag.presentation.controller;

import com.kimtaehoonki.task.tag.presentation.dto.DeleteTagResponseDto;
import com.kimtaehoonki.task.tag.presentation.dto.RegisterTagRequestDto;
import com.kimtaehoonki.task.tag.presentation.dto.RegisterTagResponseDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {
    /**
     * 태그를 등록한다.
     *
     * @param requestDto @RequestBody
     * @return RegisterTagResponseDto
     */
    @PostMapping("/tags")
    public RegisterTagResponseDto registerTag(@RequestBody RegisterTagRequestDto requestDto) {
        return null;
    }

    /**
     * 해당 태그를 삭제한다.
     *
     * @param tagId @PathVariable
     * @return DeleteTagResponseDto
     */
    @DeleteMapping("/tags/{id}")
    public DeleteTagResponseDto deleteTag(@PathVariable("id") Long tagId) {
        return null;
    }
}
