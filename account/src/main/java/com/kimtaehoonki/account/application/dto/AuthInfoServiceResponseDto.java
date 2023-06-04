package com.kimtaehoonki.account.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthInfoServiceResponseDto {
    @Getter
    private Integer memberId;

    private String password;

    public boolean checkPasswordIsSame(String password) {
        return this.password.equals(password);
    }
}
