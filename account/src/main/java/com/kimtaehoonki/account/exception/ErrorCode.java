package com.kimtaehoonki.account.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EMAIL_DUPLICATE("해당되는 이메일로 가입된 계정이 존재합니다"),
    USERNAME_DUPLICATE("해당되는 아이디로 가입된 계정이 존재합니다"),
    USER_NOT_FOUND("해당되는 회원을 찾을 수 없습니다"),
    INVALID_PARAM_WHEN_SEARCH_AUTH_INFO("email 혹은 username 하나만 입력해주세요");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
