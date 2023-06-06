package com.kimtaehoonki.account.presentation;

import com.kimtaehoonki.account.application.MemberService;
import com.kimtaehoonki.account.application.dto.response.AuthInfo;
import com.kimtaehoonki.account.presentation.dto.request.MemberRegisterRequestDto;
import com.kimtaehoonki.account.presentation.dto.response.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
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
    public ResponseEntity<Integer> register(@RequestBody MemberRegisterRequestDto dto) {
        Integer memberId = memberService.register(dto);
        return new ResponseEntity<>(memberId, HttpStatus.CREATED);
    }


    /**
     * 회원의 id를 통해 회원의 정보를 조회한다
     * 존재하지 않는 id가 입력된 경우, UserNotFoundException을 반환한다
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberInfo> showMemberInfo(@PathVariable("id") Integer memberId) {
        MemberInfo result = memberService.findMember(memberId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * email을 통해서 사용자의 정보를 조회한다
     * OAuth를 지원하기 위해 존재합니다
     */
    @GetMapping("/search")
    public void showMemberInfoUsingEmail(@RequestParam String email) {

    }

    /**
     *
     */
    @GetMapping("/{id}/password")
    public ResponseEntity<MemberInfo> showPassword(@PathVariable("id") Integer memberId) {
        MemberInfo result = memberService.findMember(memberId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}

