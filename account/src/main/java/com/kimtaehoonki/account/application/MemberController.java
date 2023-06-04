package com.kimtaehoonki.account.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @PostMapping("/auth/login")
    public void login() {

    }

    @PostMapping("/auth/login/github")
    public void loginUsingGithub() {

    }

    @PostMapping("/auth/logout")
    public void logout() {

    }

    @PostMapping("/auth/register")
    public void register() {

    }

    @GetMapping("/users/{id}")
    public void getUsers(@PathVariable("id") Integer userId) {

    }

}

