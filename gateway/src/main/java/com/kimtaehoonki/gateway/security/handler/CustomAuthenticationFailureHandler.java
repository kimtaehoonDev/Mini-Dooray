package com.kimtaehoonki.gateway.security.handler;

import com.kimtaehoonki.gateway.security.exception.AuthenticationOAuth2UserNotFoundException;
import com.kimtaehoonki.gateway.security.exception.AuthenticationRestTemplateException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        log.info("CustomAuthenticationEntryPoint");

        HttpSession session = request.getSession(true);

        if (exception instanceof InternalAuthenticationServiceException) {
            log.info("InternalAuthenticationServiceException");
            session.setAttribute("ex", exception.getMessage());
            response.sendRedirect("/login");
            return;
        }

        if (exception instanceof AuthenticationRestTemplateException) {
            session.setAttribute("ex", exception.getMessage());
            response.sendRedirect("/login");
            return;

        }

        if (exception instanceof AuthenticationOAuth2UserNotFoundException) {
            log.info("AuthenticationOAuth2UserNotFoundException");
            session.setAttribute("ex", exception.getMessage());
            session.setAttribute("email",
                    ((AuthenticationOAuth2UserNotFoundException) exception).getEmail());
            response.sendRedirect("/register");
            return;
        }


        session.setAttribute("ex", "fail to login");
        response.sendRedirect("/login");
    }
}
