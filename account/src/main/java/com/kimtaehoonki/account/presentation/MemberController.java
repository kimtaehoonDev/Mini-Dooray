package com.kimtaehoonki.account.presentation;

import com.kimtaehoonki.account.application.MemberService;
import com.kimtaehoonki.account.application.dto.AuthInfo;
import com.kimtaehoonki.account.exception.UserEmailDuplicateException;
import com.kimtaehoonki.account.exception.UserNotFoundException;
import com.kimtaehoonki.account.exception.UserNotMatchException;
import com.kimtaehoonki.account.exception.UsernameDuplicateException;
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
     * id와 패스워드를 통해 권한을 가져온다
     */
    @PostMapping("/login")
    public ResponseEntity<AuthInfo> login(LoginRequestDto dto) {
        try {
            AuthInfo info = memberService.getAuthInfo(dto.getUsername(), dto.getPassword());
            return new ResponseEntity<>(info, HttpStatus.CREATED);
        } catch (UserNotMatchException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Integer> register(@RequestBody RegisterRequestDto dto) {
        try {
            Integer memberId = memberService.register(dto);
            return new ResponseEntity<>(memberId, HttpStatus.CREATED);
        } catch (UsernameDuplicateException | UserEmailDuplicateException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<GetMemberResponseDto> getMember(@PathVariable("id") Integer userId) {
        try {
            GetMemberResponseDto result = memberService.findMember(userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

}

