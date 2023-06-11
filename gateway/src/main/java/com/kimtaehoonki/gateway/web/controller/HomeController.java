package com.kimtaehoonki.gateway.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // TODO 로그인성공시 loginHome
    @GetMapping("/")
    public String home() {
        return "home";
    }

    // TODO temp
    @GetMapping("/loginHome")
    public String loginHome() {
        return "loginHome";
    }
}
