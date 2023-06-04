package com.kimtaehoonki.account.exception.impl;

import com.kimtaehoonki.account.exception.CustomException;
import com.kimtaehoonki.account.exception.ErrorCode;

public class UserEmailDuplicateException extends CustomException {
    public UserEmailDuplicateException() {
        super(ErrorCode.USER_EMAIL_DUPLICATE);
    }
}
