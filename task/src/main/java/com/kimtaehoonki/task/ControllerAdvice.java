package com.kimtaehoonki.task;

import com.kimtaehoonki.task.exception.ErrorResponse;
import com.kimtaehoonki.task.exception.impl.AlreadyProjectMemberException;
import com.kimtaehoonki.task.exception.impl.AuthenticationException;
import com.kimtaehoonki.task.exception.impl.AuthorizedException;
import com.kimtaehoonki.task.exception.impl.CommentNotFoundException;
import com.kimtaehoonki.task.exception.impl.MemberNotFoundException;
import com.kimtaehoonki.task.exception.impl.MilestoneNotFoundException;
import com.kimtaehoonki.task.exception.impl.PageParamInvalidException;
import com.kimtaehoonki.task.exception.impl.ProjectExitException;
import com.kimtaehoonki.task.exception.impl.ProjectNameDuplicateException;
import com.kimtaehoonki.task.exception.impl.ProjectNotFoundException;
import com.kimtaehoonki.task.exception.impl.ResourceNameDuplicatedException;
import com.kimtaehoonki.task.exception.impl.StartDateLaterThanEndDateException;
import com.kimtaehoonki.task.exception.impl.TagNotFoundException;
import com.kimtaehoonki.task.exception.impl.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({
        IllegalArgumentException.class,
        PageParamInvalidException.class,
        StartDateLaterThanEndDateException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse unauthorized(Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(AuthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse forbidden(Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler({
        MemberNotFoundException.class,
        ProjectNotFoundException.class,
        ProjectExitException.class,
        CommentNotFoundException.class,
        MilestoneNotFoundException.class,
        TagNotFoundException.class,
        TaskNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFound(Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler({
        ProjectNameDuplicateException.class,
        AlreadyProjectMemberException.class,
        ResourceNameDuplicatedException.class,
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse conflict(Exception exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
