package com.kimtaehoonki.account.exception;

public class UserNotMatchException extends IllegalArgumentException {
    public UserNotMatchException() {
        super("아이디가 존재하지 않거나, 패스워드가 잘못되었습니다");
    }
}
