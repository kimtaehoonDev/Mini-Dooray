package com.kimtaehoonki.account;

import com.kimtaehoonki.account.exception.ErrorCode;
import com.kimtaehoonki.account.exception.ErrorResponse;
import com.kimtaehoonki.account.exception.impl.UserEmailDuplicateException;
import com.kimtaehoonki.account.exception.impl.UserNotFoundException;
import com.kimtaehoonki.account.exception.impl.UserNotMatchException;
import com.kimtaehoonki.account.exception.impl.UsernameDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({
        UserNotMatchException.class,
        UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFound(Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler({
        UsernameDuplicateException.class,
        UserEmailDuplicateException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse conflict(Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
