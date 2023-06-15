package com.kimtaehoonki.task.exception.impl;

import com.kimtaehoonki.task.exception.CustomException;
import com.kimtaehoonki.task.exception.ErrorCode;

public class TagNameDuplicatedException extends CustomException {
    public TagNameDuplicatedException() {
        super(ErrorCode.TAG_NAME_DUPLICATED);
    }
}
