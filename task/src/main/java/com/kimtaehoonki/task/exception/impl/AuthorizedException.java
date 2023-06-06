package com.kimtaehoonki.task.exception.impl;

import com.kimtaehoonki.task.exception.CustomException;
import com.kimtaehoonki.task.exception.ErrorCode;

public class AuthorizedException extends CustomException {
    public AuthorizedException() {

        super(ErrorCode.AUTHORIZED);
    }
}
