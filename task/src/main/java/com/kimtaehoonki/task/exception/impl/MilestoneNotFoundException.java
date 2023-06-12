package com.kimtaehoonki.task.exception.impl;

import com.kimtaehoonki.task.exception.CustomException;
import com.kimtaehoonki.task.exception.ErrorCode;

public class MilestoneNotFoundException extends CustomException {
    public MilestoneNotFoundException() {
        super(ErrorCode.MILESTONE_NOT_FOUND);
    }
}
