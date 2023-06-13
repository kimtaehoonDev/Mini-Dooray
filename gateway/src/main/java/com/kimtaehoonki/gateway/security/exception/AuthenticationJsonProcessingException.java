package com.kimtaehoonki.gateway.security.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationJsonProcessingException extends AuthenticationException {
    public AuthenticationJsonProcessingException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationJsonProcessingException(String msg) {
        super(msg);
    }
}
