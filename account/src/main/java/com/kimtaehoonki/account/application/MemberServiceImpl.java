package com.kimtaehoonki.account.application;

import com.kimtaehoonki.account.application.dto.response.AuthInfo;
import com.kimtaehoonki.account.domain.Member;
import com.kimtaehoonki.account.domain.MemberRepository;
import com.kimtaehoonki.account.domain.MemberStatus;
import com.kimtaehoonki.account.exception.impl.UserEmailDuplicateException;
import com.kimtaehoonki.account.exception.impl.UserNotFoundException;
import com.kimtaehoonki.account.exception.impl.UsernameDuplicateException;
import com.kimtaehoonki.account.presentation.dto.request.MemberRegisterRequestDto;
import com.kimtaehoonki.account.presentation.dto.response.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

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
        MemberInfo memberInfo = memberRepository.findById(memberId, MemberInfo.class)
            .orElseThrow(UserNotFoundException::new);

        if (memberInfo.getStatus() == MemberStatus.DORMANCY) {
            throw new UserNotFoundException();
        }
        return memberInfo;
    }

    @Override
    public AuthInfo showAuthInfo(Integer memberId) {
        AuthInfo authInfo = memberRepository.findById(memberId, AuthInfo.class)
            .orElseThrow(UserNotFoundException::new);

        if (authInfo.getStatus() == MemberStatus.DORMANCY) {
            throw new UserNotFoundException();
        }
        return authInfo;
    }

    @Override
    public AuthInfo findMemberUsingEmail(String email) {
        AuthInfo authInfo = memberRepository.findByEmail(email)
            .orElseThrow(UserNotFoundException::new);

        if (authInfo.getStatus() == MemberStatus.DORMANCY) {
            throw new UserNotFoundException();
        }
        return authInfo;
    }
}
