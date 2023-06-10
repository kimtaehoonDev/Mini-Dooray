package com.kimtaehoonki.gateway.security.handler;

import com.kimtaehoonki.gateway.security.exception.AuthenticationRestTemplateException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        log.info("CustomAuthenticationEntryPoint");
        if (exception instanceof AuthenticationRestTemplateException) {
            log.info("authenticationEntryPoint");

            request.getRequestDispatcher("/login").forward(request, response);
        }

    }
}
