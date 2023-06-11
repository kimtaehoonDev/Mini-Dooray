package com.kimtaehoonki.gateway.security.handler;

import com.kimtaehoonki.gateway.security.dto.MemberSecurityDto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        log.info("onAuthenticationSuccess");
        String sessionId = UUID.randomUUID().toString();



        Cookie cookie = new Cookie("KING", sessionId);
        cookie.setPath("/");
        cookie.setMaxAge(259200);
        response.addCookie(cookie);

        MemberSecurityDto securityDto = (MemberSecurityDto) authentication.getPrincipal();
        Integer id = securityDto.getId();
        String username = securityDto.getUsername();
        String authority = new ArrayList<>(securityDto.getAuthorities()).get(0).getAuthority();

        redisTemplate.opsForHash().put(sessionId, "id", String.valueOf(id));
        redisTemplate.opsForHash().put(sessionId, "username", username);
        redisTemplate.opsForHash().put(sessionId, "authority", authority);

//        getRedirectStrategy().sendRedirect(request, response, "/");
        response.sendRedirect("/");
    }
}
