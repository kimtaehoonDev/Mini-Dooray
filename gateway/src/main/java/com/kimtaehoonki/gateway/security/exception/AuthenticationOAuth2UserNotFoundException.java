package com.kimtaehoonki.gateway.security.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationOAuth2UserNotFoundException extends AuthenticationException {
    @Getter
    private String email;
    public AuthenticationOAuth2UserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationOAuth2UserNotFoundException(String msg, String email) {
        super(msg);
        this.email = email;
    }
}
