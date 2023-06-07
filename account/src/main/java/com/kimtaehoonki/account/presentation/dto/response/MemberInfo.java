package com.kimtaehoonki.account.presentation.dto.response;

import com.kimtaehoonki.account.domain.MemberStatus;

public interface MemberInfo {
    Integer getId();
    String getUsername();
    String getName();
    String getEmail();
    String getPhoneNum();

    MemberStatus getStatus();
}
