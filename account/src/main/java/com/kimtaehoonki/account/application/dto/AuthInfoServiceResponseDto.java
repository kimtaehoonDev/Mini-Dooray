package com.kimtaehoonki.account.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AuthInfoServiceResponseDto {
    @Getter
    private String id;

    private String password;

    public boolean checkPasswordIsSame(String password) {
        return this.password.equals(password);
    }
}
