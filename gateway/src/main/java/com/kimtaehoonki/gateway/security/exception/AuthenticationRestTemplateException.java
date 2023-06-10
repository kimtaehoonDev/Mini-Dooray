package com.kimtaehoonki.gateway.security.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationRestTemplateException extends AuthenticationException {
    public AuthenticationRestTemplateException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationRestTemplateException(String msg) {
        super(msg);
    }
}
