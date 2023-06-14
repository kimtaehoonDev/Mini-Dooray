package com.kimtaehoonki.gateway.security.service;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.kimtaehoonki.gateway.security.dto.AuthInfoResponseDto;
import com.kimtaehoonki.gateway.security.dto.MemberSecurityDto;
import com.kimtaehoonki.gateway.security.exception.AuthenticationRestTemplateException;
import com.kimtaehoonki.gateway.security.exception.AuthenticationUserNotFoundException;
import com.kimtaehoonki.gateway.security.dto.ExceptionDto;
import com.kimtaehoonki.gateway.utils.JsonUtils;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements org.springframework.security.core.userdetails.UserDetailsService {
    private final RestTemplate restTemplate;
    private final JsonUtils jsonUtils;

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
            HttpClientErrorException ex = (HttpClientErrorException) e;
            String responseBodyAsString = ex.getResponseBodyAsString();

                ExceptionDto exceptionDto =
                        (ExceptionDto) jsonUtils.readValue(responseBodyAsString, ExceptionDto.class);

            throw new AuthenticationRestTemplateException(exceptionDto.getMessage(), e);
        }

        throw new AuthenticationServiceException("오류발생");
    }
}
