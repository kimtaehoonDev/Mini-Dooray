package com.kimtaehoonki.task.exception.impl;

import com.kimtaehoonki.task.exception.CustomException;
import com.kimtaehoonki.task.exception.ErrorCode;

public class ProjectNameDuplicateException extends CustomException {
    public ProjectNameDuplicateException() {

        super(ErrorCode.PROJECT_NAME_DUPLICATE);
    }
}
