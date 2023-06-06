package com.kimtaehoonki.account.application;

import com.kimtaehoonki.account.application.dto.response.AuthInfo;
import com.kimtaehoonki.account.presentation.dto.request.MemberRegisterRequestDto;
import com.kimtaehoonki.account.presentation.dto.response.MemberInfo;

public interface MemberService {

    /**
     * Member를 저장한다
     * 저장에 성공하면 저장되는 Member의 id를 반환한다
     */
    Integer register(MemberRegisterRequestDto dto);

    /**
     * Member 객체의 id를 사용해 특정 멤버의 민감정보를 제외한 정보를 가져온다
     * 민감정보에는 password가 존재한다
     */
    MemberInfo findMember(Integer memberId);

    AuthInfo showAuthInfo(Integer memberId);

    AuthInfo findMemberUsingEmail(String email);
}
