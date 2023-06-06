package com.kimtaehoonki.gateway.security.service;

import com.kimtaehoonki.gateway.security.dto.MemberIdPasswordResponseDto;
import com.kimtaehoonki.gateway.security.dto.MemberSecurityDto;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class CustomUserDetailsService
        implements org.springframework.security.core.userdetails.UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    public CustomUserDetailsService(RestTemplate restTemplate) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String url = "http://localhost:9091/members/{username}/password";
        MemberIdPasswordResponseDto responseDto =
                restTemplate.getForEntity(url,MemberIdPasswordResponseDto.class,
                        username).getBody();

        return new MemberSecurityDto(String.valueOf(responseDto.getId()), passwordEncoder.encode(responseDto.getPassword()),
                Collections.singleton(new SimpleGrantedAuthority(responseDto.getAuthority())));
    }
}
