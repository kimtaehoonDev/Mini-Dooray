package com.kimtaehoonki.gateway.security.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationOAuth2UserNotFoundException extends AuthenticationException {
    public AuthenticationOAuth2UserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationOAuth2UserNotFoundException(String msg) {
        super(msg);
    }
}
