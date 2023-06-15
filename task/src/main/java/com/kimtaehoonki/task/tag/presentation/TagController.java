package com.kimtaehoonki.task.tag.presentation;

import com.kimtaehoonki.task.tag.application.TagService;
import com.kimtaehoonki.task.tag.dto.RegisterTagRequestDto;
import com.kimtaehoonki.task.utils.ErrorMessageBinder;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
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
    public void registerTag(@RequestBody @Valid RegisterTagRequestDto dto, BindingResult bindingResult,
                            @CookieValue Integer memberId) {
        ErrorMessageBinder.throwErrorWithErrorBinding(bindingResult);
        String name = dto.getName();
        Long projectId = dto.getProjectId();
        tagService.registerTag(name, projectId, memberId);
    }

    /**
     * 해당 태그를 삭제한다.
     *
     */
    @DeleteMapping("/tags/{id}")
    public void deleteTag(@PathVariable("id") Long tagId,
                          @CookieValue Integer memberId) {
        tagService.deleteTag(tagId, memberId);
    }
}
