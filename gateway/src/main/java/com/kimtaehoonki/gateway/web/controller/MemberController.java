package com.kimtaehoonki.gateway.web.controller;

import com.kimtaehoonki.gateway.web.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(
            @ModelAttribute("requestDto") MemberRegisterRequestDto memberRegisterRequestDto) {
        return "register";
    }

    @PostMapping("/register")
    public String registerMember(@Valid @ModelAttribute MemberRegisterRequestDto requestDto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        Integer userId = memberService.register(requestDto);

        return "redirect:/home";
    }
}
