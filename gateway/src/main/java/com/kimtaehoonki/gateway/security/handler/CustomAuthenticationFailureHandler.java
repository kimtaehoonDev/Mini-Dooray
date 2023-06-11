package com.kimtaehoonki.gateway.security.handler;

import com.kimtaehoonki.gateway.security.exception.AuthenticationOAuth2UserNotFoundException;
import com.kimtaehoonki.gateway.security.exception.AuthenticationRestTemplateException;
import com.kimtaehoonki.gateway.utils.CookieUtils;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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

        String message = CookieUtils.removeInvalidCharacter(exception.getMessage());
        Cookie ex = new Cookie("ex", message);
        ex.setPath("/");
        response.addCookie(ex);

        if (exception instanceof InternalAuthenticationServiceException) {
            log.info("InternalAuthenticationServiceException");
            response.sendRedirect("/login");
            return;
        }

        if (exception instanceof AuthenticationRestTemplateException) {
            response.sendRedirect("/login");
            return;

        }

        if (exception instanceof AuthenticationOAuth2UserNotFoundException) {
            log.info("AuthenticationOAuth2UserNotFoundException");
            Cookie email = new Cookie("email",
                    ((AuthenticationOAuth2UserNotFoundException) exception).getEmail());
            email.setPath("/");
            response.addCookie(email);
            response.sendRedirect("/register");
            return;
        }


        response.sendRedirect("/login");
    }
}
