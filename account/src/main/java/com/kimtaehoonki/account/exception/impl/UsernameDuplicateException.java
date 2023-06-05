package com.kimtaehoonki.account.exception.impl;

import com.kimtaehoonki.account.exception.CustomException;
import com.kimtaehoonki.account.exception.ErrorCode;

public class UsernameDuplicateException extends CustomException {
    public UsernameDuplicateException() {
        super(ErrorCode.USERNAME_DUPLICATE);
    }
}
