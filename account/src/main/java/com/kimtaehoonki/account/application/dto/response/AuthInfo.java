package com.kimtaehoonki.account.application.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthInfo {
    @Getter
    private Integer memberId;

    public static AuthInfo of(AuthInfoServiceResponseDto dto) {
        return new AuthInfo(dto.getId());
    }
}