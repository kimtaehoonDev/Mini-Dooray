package com.kimtaehoonki.task.exception.impl;

import com.kimtaehoonki.task.exception.CustomException;
import com.kimtaehoonki.task.exception.ErrorCode;

public class AuthenticationException extends CustomException {
    public AuthenticationException() {

        super(ErrorCode.AUTHENTICATED);
    }
}
