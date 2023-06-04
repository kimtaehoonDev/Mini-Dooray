package com.kimtaehoonki.account.exception;

public class UsernameDuplicateException extends IllegalArgumentException {
    public UsernameDuplicateException() {
        super("해당되는 아이디로 가입된 계정이 존재합니다");
    }
}
