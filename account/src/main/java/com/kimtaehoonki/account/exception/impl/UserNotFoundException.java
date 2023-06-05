package com.kimtaehoonki.account.exception.impl;

import com.kimtaehoonki.account.exception.CustomException;
import com.kimtaehoonki.account.exception.ErrorCode;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
