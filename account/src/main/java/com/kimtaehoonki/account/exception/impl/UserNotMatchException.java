package com.kimtaehoonki.account.exception.impl;

import com.kimtaehoonki.account.exception.CustomException;
import com.kimtaehoonki.account.exception.ErrorCode;

public class UserNotMatchException extends CustomException {
    public UserNotMatchException() {
        super(ErrorCode.USER_NOT_MATCH);
    }
}
