package com.kimtaehoonki.account.presentation;

import com.kimtaehoonki.account.application.MemberService;
import com.kimtaehoonki.account.application.dto.response.AuthInfo;
import com.kimtaehoonki.account.presentation.dto.request.MemberRegisterRequestDto;
import com.kimtaehoonki.account.presentation.dto.response.MemberInfo;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public Integer register(@RequestBody @Valid MemberRegisterRequestDto dto) {
       return memberService.register(dto);
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
     * OAuth를 지원하기 위해 존재합니다
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
     */
    @GetMapping("/{id}/auth-info")
    @ResponseStatus(HttpStatus.OK)
    public AuthInfo showAuthInfo(@PathVariable("id") Integer memberId) {
        return memberService.showAuthInfo(memberId);
    }

}

