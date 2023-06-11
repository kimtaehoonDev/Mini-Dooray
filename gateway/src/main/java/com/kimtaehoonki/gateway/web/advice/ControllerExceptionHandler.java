package com.kimtaehoonki.gateway.web.advice;

import com.kimtaehoonki.gateway.web.controller.MemberRegisterRequestDto;
import com.kimtaehoonki.gateway.web.exception.MemberRegisterException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(MemberRegisterException.class)
    public String memberRegisterExceptionHandler(Exception ex, Model model) {
        model.addAttribute("ex", ex.getMessage());
        model.addAttribute("requestDto", new MemberRegisterRequestDto());

        return "register";
    }
}
