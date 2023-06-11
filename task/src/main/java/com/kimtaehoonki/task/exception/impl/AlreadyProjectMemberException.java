package com.kimtaehoonki.task.exception.impl;

import com.kimtaehoonki.task.exception.CustomException;
import com.kimtaehoonki.task.exception.ErrorCode;

public class AlreadyProjectMemberException extends CustomException {
    public AlreadyProjectMemberException() {

        super(ErrorCode.ALREADY_PROJECT_MEMBER);
    }
}
