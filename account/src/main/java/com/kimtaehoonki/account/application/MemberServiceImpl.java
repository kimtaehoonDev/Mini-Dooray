package com.kimtaehoonki.account.application;

import com.kimtaehoonki.account.application.dto.AuthInfo;
import com.kimtaehoonki.account.application.dto.AuthInfoServiceResponseDto;
import com.kimtaehoonki.account.domain.Member;
import com.kimtaehoonki.account.domain.MemberRepository;
import com.kimtaehoonki.account.exception.impl.UserEmailDuplicateException;
import com.kimtaehoonki.account.exception.impl.UserNotFoundException;
import com.kimtaehoonki.account.exception.impl.UserNotMatchException;
import com.kimtaehoonki.account.exception.impl.UsernameDuplicateException;
import com.kimtaehoonki.account.presentation.dto.MemberRegisterRequestDto;
import com.kimtaehoonki.account.presentation.dto.response.MemberInfo;
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
        AuthInfoServiceResponseDto dto =
            memberRepository.findByUsername(username, AuthInfoServiceResponseDto.class)
                .orElseThrow(UserNotMatchException::new);
        boolean isCorrectPassword = dto.checkPasswordIsSame(password);
        if (!isCorrectPassword) {
            throw new UserNotMatchException();
        }

        return AuthInfo.of(dto);
    }

    @Transactional
    @Override
    public Integer register(MemberRegisterRequestDto dto) {
        boolean isIdAlreadyExist = memberRepository.existsByUsername(dto.getUsername());
        if (isIdAlreadyExist) {
            throw new UsernameDuplicateException();
        }
        boolean isEmailAlreadyExist = memberRepository.existsByEmail(dto.getEmail());
        if (isEmailAlreadyExist) {
            throw new UserEmailDuplicateException();
        }

        Member createdMember = memberRepository.save(dto.makeMember());
        return createdMember.getId();
    }

    @Override
    public MemberInfo findMember(Integer memberId) {
        return memberRepository.findMemberById(memberId)
                .orElseThrow(UserNotFoundException::new);
    }
}
