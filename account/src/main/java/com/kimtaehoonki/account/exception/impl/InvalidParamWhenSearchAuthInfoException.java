package com.kimtaehoonki.account.exception.impl;

import com.kimtaehoonki.account.exception.CustomException;
import com.kimtaehoonki.account.exception.ErrorCode;

public class InvalidParamWhenSearchAuthInfoException extends CustomException {
    public InvalidParamWhenSearchAuthInfoException() {
        super(ErrorCode.INVALID_PARAM_WHEN_SEARCH_AUTH_INFO);
    }
}
