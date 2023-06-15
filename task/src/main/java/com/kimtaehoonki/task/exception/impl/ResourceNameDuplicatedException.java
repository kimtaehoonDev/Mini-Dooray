package com.kimtaehoonki.task.exception.impl;

import com.kimtaehoonki.task.exception.CustomException;
import com.kimtaehoonki.task.exception.ErrorCode;

public class ResourceNameDuplicatedException extends CustomException {
    public ResourceNameDuplicatedException() {
        super(ErrorCode.RESOURCE_NAME_DUPLICATED);
    }
}
