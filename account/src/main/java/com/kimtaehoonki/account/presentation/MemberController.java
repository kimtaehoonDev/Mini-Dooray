package com.kimtaehoonki.account.presentation;

import com.kimtaehoonki.account.application.MemberService;
import com.kimtaehoonki.account.application.dto.AuthInfo;
import com.kimtaehoonki.account.presentation.dto.LoginRequestDto;
import com.kimtaehoonki.account.presentation.dto.RegisterRequestDto;
import com.kimtaehoonki.account.presentation.dto.response.GetMemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * id와 패스워드를 통해 인증에 관련된 정보들을 가져옵니다
     * id, password가 매치되는 게 없으면 UserNotMatchException을 반환합니다
     */
    @PostMapping("/login")
    public ResponseEntity<AuthInfo> login(LoginRequestDto dto) {
        AuthInfo info = memberService.getAuthInfo(dto.getUsername(), dto.getPassword());
        return new ResponseEntity<>(info, HttpStatus.CREATED);
    }

    /**
     * 회원을 등록합니다.
     * 회원의 아이디가 중복된 경우 UsernameDuplicateException을 반환합니다
     * 회원의 이메일이 중복된 경우 UserEmailDuplicateException을 반환합니다
     */
    @PostMapping("/users")
    public ResponseEntity<Integer> register(@RequestBody RegisterRequestDto dto) {
        Integer memberId = memberService.register(dto);
        return new ResponseEntity<>(memberId, HttpStatus.CREATED);
    }

    /**
     * 회원의 id를 통해 회원의 정보를 조회합니다
     * 존재하지 않는 id가 입력된 경우, UserNotFoundException을 반환합니다
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<GetMemberResponseDto> getMember(@PathVariable("id") Integer userId) {
        GetMemberResponseDto result = memberService.findMember(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}

