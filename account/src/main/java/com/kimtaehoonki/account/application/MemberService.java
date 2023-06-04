package com.kimtaehoonki.account.application;

import com.kimtaehoonki.account.application.dto.AuthInfo;
import com.kimtaehoonki.account.presentation.dto.MemberRegisterRequestDto;
import com.kimtaehoonki.account.presentation.dto.response.GetMemberResponseDto;

public interface MemberService {
    AuthInfo getAuthInfo(String username, String password);

    Integer register(MemberRegisterRequestDto dto);

    GetMemberResponseDto findMember(Integer userId);
}
