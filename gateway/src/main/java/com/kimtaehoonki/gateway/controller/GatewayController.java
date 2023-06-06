package com.kimtaehoonki.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GatewayController {
    @GetMapping("/")
    public String home() {
        return "login-success";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    @PostMapping("/login")
    public String doLogin() {
        return "redirect:/login";
    }

    @GetMapping("/login-success")
    public String loginSuccess() {
        return "login-success";
    }
}
