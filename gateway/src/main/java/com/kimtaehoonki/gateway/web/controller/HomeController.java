package com.kimtaehoonki.gateway.web.controller;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/")
    public String home(@CookieValue(name = "KING", required = false) String sessionId,
                       Model model) {
        if (Objects.isNull(sessionId)) {
            return "home";
        }


        String username = (String) (redisTemplate.opsForHash().get(sessionId, "username"));
        model.addAttribute("username", username);

        return "loginHome";
    }
}
