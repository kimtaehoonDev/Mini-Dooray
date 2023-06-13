package com.kimtaehoonki.task.exception.impl;

import com.kimtaehoonki.task.exception.CustomException;
import com.kimtaehoonki.task.exception.ErrorCode;

public class ProjectExitException extends CustomException {
    public ProjectExitException() {

        super(ErrorCode.PROJECT_EXIT);
    }
}
