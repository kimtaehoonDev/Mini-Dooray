package com.kimtaehoonki.account.presentation;

import com.kimtaehoonki.account.application.MemberService;
import com.kimtaehoonki.account.application.dto.response.AuthInfo;
import com.kimtaehoonki.account.presentation.dto.request.MemberRegisterRequestDto;
import com.kimtaehoonki.account.presentation.dto.response.MemberInfo;
import com.kimtaehoonki.account.presentation.dto.response.RegisterResponseDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    /**
     * 회원을 등록한다
     * 회원의 아이디가 중복된 경우 UsernameDuplicateException을 반환한다
     * 회원의 이메일이 중복된 경우 UserEmailDuplicateException을 반환한다
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponseDto register(@RequestBody @Valid MemberRegisterRequestDto dto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindMessageBeforeThrowError(bindingResult);
        }
        Integer newMemberId = memberService.register(dto);
        return new RegisterResponseDto(newMemberId);
    }


    /**
     * 회원의 id를 통해 회원의 정보를 조회한다
     * 존재하지 않는 id가 입력된 경우, UserNotFoundException을 반환한다
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MemberInfo showMemberInfo(@PathVariable("id") Integer memberId) {
        return memberService.findMember(memberId);
    }


    /**
     * email을 통해서 사용자의 정보를 조회한다
     * OAuth를 지원하기 위해 존재한다
     * 존재하지 않는 email이 입력된 경우, UserNotFoundException을 반환한다
     *
     * @return
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public AuthInfo showAuthInfoUsingEmail(@RequestParam String email) {
        return memberService.findMemberUsingEmail(email);
    }


    /**
     * MemberId를 통해 암호화된 Password를 반환한다
     * 존재하지 않는 id가 입력된 경우, UserNotFoundException을 반환한다
     */
    @GetMapping("/{id}/auth-info")
    @ResponseStatus(HttpStatus.OK)
    public AuthInfo showAuthInfo(@PathVariable("id") Integer memberId) {
        return memberService.showAuthInfo(memberId);
    }

    private void bindMessageBeforeThrowError(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .append("; ");
        }
        throw new IllegalArgumentException(errorMessage.toString());
    }

}