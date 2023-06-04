package com.kimtaehoonki.account.application;

import com.kimtaehoonki.account.application.dto.AuthInfo;
import com.kimtaehoonki.account.application.dto.FindInfoResponseDto;
import com.kimtaehoonki.account.domain.Member;
import com.kimtaehoonki.account.domain.MemberRepository;
import com.kimtaehoonki.account.exception.impl.UserNotMatchException;
import com.kimtaehoonki.account.presentation.dto.RegisterRequestDto;
import com.kimtaehoonki.account.presentation.dto.response.GetMemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public AuthInfo getAuthInfo(String username, String password) {
        FindInfoResponseDto dto = memberRepository.findInfoByUsername(username)
            .orElseThrow(UserNotMatchException::new);
        boolean isCorrectPassword = dto.checkPasswordIsSame(password);
        if (!isCorrectPassword) {
            throw new UserNotMatchException();
        }

        return AuthInfo.of(dto);
    }

    @Override
    public Integer register(RegisterRequestDto dto) {
        return null;
    }

    @Override
    public GetMemberResponseDto findMember(Integer userId) {
        return null;
    }
}
