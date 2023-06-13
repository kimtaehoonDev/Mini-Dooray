package com.kimtaehoonki.task.exception.impl;

import com.kimtaehoonki.task.exception.CustomException;
import com.kimtaehoonki.task.exception.ErrorCode;

public class CommentNotFoundException extends CustomException {
    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
