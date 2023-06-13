package com.kimtaehoonki.gateway.web.controller;

import com.kimtaehoonki.gateway.utils.CookieUtils;
import com.kimtaehoonki.gateway.web.service.MemberService;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(
            HttpServletRequest req, HttpServletResponse resp,
            Model model) {
        getModelFromCookie(req, resp, "ex", model);

        return "login";
    }


    @GetMapping("/register")
    public String showRegisterForm(
            HttpServletRequest req, HttpServletResponse resp,
            @ModelAttribute("requestDto") MemberRegisterRequestDto memberRegisterRequestDto,
            Model model) {
        getModelFromCookie(req, resp, "ex", model);
        getModelFromCookie(req, resp, "email", model);

        return "register";
    }

    @PostMapping("/register")
    public String registerMember(@ModelAttribute MemberRegisterRequestDto requestDto,
                                 HttpServletRequest req, HttpServletResponse resp,
                                 Model model) {
        Integer userId = memberService.register(requestDto);
        getModelFromCookie(req, resp, "ex", model);

        return "redirect:/login";
    }

    private static void getModelFromCookie(HttpServletRequest req, HttpServletResponse resp,
                                           String cookieName, Model model) {
        String cookieValue = CookieUtils.getCookieValueAsString(req, cookieName);
        if (Objects.nonNull(cookieValue)) {
            cookieValue = CookieUtils.resetWhitespace(cookieValue);
        }

        model.addAttribute("" + cookieName, cookieValue);
        CookieUtils.deleteCookie(req, resp, cookieName);
    }

}
