package com.kimtaehoonki.account.exception;

public class UserNotFoundException extends IllegalArgumentException {
    public UserNotFoundException() {
        super("해당되는 회원을 찾을 수 없습니다");
    }
}
