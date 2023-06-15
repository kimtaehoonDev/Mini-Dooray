package com.kimtaehoonki.task.exception.impl;

import com.kimtaehoonki.task.exception.CustomException;
import com.kimtaehoonki.task.exception.ErrorCode;

public class PageParamInvalidException extends CustomException {
    public PageParamInvalidException() {
        super(ErrorCode.PAGE_PARAM_INVALID);
    }
}
