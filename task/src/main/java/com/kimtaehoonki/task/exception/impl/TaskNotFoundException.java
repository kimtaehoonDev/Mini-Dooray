package com.kimtaehoonki.task.exception.impl;

import com.kimtaehoonki.task.exception.CustomException;
import com.kimtaehoonki.task.exception.ErrorCode;

public class TaskNotFoundException extends CustomException {
    public TaskNotFoundException() {
        super(ErrorCode.TASK_NOT_FOUND);
    }
}
