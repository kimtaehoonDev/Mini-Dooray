package com.kimtaehoonki.account.application.dto;

import lombok.Getter;

public class FindInfoResponseDto {
    @Getter
    private String id;

    private String password;

    public boolean checkPasswordIsSame(String password) {
        return this.password.equals(password);
    }
}
