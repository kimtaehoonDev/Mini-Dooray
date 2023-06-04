package com.kimtaehoonki.account.exception;

public class UserEmailDuplicateException extends IllegalArgumentException {
    public UserEmailDuplicateException() {
        super("해당되는 이메일로 가입된 계정이 존재합니다");
    }
}
