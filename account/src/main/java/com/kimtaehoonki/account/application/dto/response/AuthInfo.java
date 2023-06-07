package com.kimtaehoonki.account.application.dto.response;

import com.kimtaehoonki.account.domain.Authority;
import com.kimtaehoonki.account.domain.MemberStatus;

public interface AuthInfo {
    Integer getId();
    String getPassword();

    MemberStatus getStatus();

    Authority getAuthority();
}
