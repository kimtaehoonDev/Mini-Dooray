package com.kimtaehoonki.task.exception.impl;

import com.kimtaehoonki.task.exception.CustomException;
import com.kimtaehoonki.task.exception.ErrorCode;

public class StartDateLaterThanEndDateException extends CustomException {
    public StartDateLaterThanEndDateException() {
        super(ErrorCode.START_DATE_LATER_THAN_END_DATE);
    }
}
