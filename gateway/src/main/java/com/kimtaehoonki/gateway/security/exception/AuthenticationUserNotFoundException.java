package com.kimtaehoonki.gateway.security.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationUserNotFoundException extends AuthenticationException {

    public AuthenticationUserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationUserNotFoundException(String msg) {
        super(msg);
    }
}
