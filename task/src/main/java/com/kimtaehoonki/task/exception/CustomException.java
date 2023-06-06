package com.kimtaehoonki.task.exception;

public class CustomException extends RuntimeException {
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
