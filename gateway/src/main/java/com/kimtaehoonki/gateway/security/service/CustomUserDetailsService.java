package com.kimtaehoonki.gateway.security.service;

import com.kimtaehoonki.gateway.security.dto.AuthInfoResponseDto;
import com.kimtaehoonki.gateway.security.dto.MemberSecurityDto;
import com.kimtaehoonki.gateway.security.exception.AuthenticationRestTemplateException;
import com.kimtaehoonki.gateway.security.exception.AuthenticationUserNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements org.springframework.security.core.userdetails.UserDetailsService {
    private final RestTemplate restTemplate;

    @Value("${kimteahoonki.accountapi.members.authInfo}")
    private String authInfoUrl;

    //    /members/{id}/auth-info
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            String urlTemplate = UriComponentsBuilder.fromHttpUrl(authInfoUrl)
                    .queryParam("username", username)
                    .encode()
                    .toUriString();

            ResponseEntity<AuthInfoResponseDto> response =
                    restTemplate.getForEntity(
                            urlTemplate,
                            AuthInfoResponseDto.class,
                            username);

            if (response.getStatusCode().is4xxClientError()) {
                throw new AuthenticationUserNotFoundException("user not found");
            }

            if (response.getStatusCode().is2xxSuccessful()) {
                AuthInfoResponseDto responseDto = response.getBody();

                return new MemberSecurityDto(
                        responseDto.getId(),
                        username,
                        responseDto.getPassword(),
                        Collections.singleton(
                                new SimpleGrantedAuthority("ROLE_" + responseDto.getAuthority())));
            }

        } catch (RestClientException e) {
            throw new AuthenticationRestTemplateException(e.getMessage(), e);
        }

        throw new RuntimeException();
    }
}
