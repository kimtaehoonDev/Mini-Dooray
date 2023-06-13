package com.kimtaehoonki.gateway.web.service;

import com.kimtaehoonki.gateway.security.dto.ExceptionDto;
import com.kimtaehoonki.gateway.utils.CookieUtils;
import com.kimtaehoonki.gateway.utils.JsonUtils;
import com.kimtaehoonki.gateway.web.controller.MemberRegisterRequestDto;
import com.kimtaehoonki.gateway.web.exception.MemberRegisterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;
    private final JsonUtils jsonUtils;


    @Value("${kimteahoonki.accountapi.members.register}")
    private String registerUrl;

    public Integer register(MemberRegisterRequestDto requestDto) {
        requestDto.setPasswordEncode(passwordEncoder);

        try {
            ResponseEntity<MemberRegisterResponseDto> response =
                    restTemplate.postForEntity(
                            registerUrl,
                            requestDto,
                            MemberRegisterResponseDto.class
                    );

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("response.status={}", response.getStatusCode());
                MemberRegisterResponseDto responseDto = response.getBody();
                return responseDto.getMemberId();
            }
        } catch (RestClientException e) {
            HttpClientErrorException ex = (HttpClientErrorException) e;
            String responseBodyAsString = ex.getResponseBodyAsString();

            ExceptionDto exceptionDto =
                    (ExceptionDto) jsonUtils.readValue(responseBodyAsString, ExceptionDto.class);
            String message = CookieUtils.removeInvalidCharacter(exceptionDto.getMessage());

            throw new MemberRegisterException(message);
        }

        throw new MemberRegisterException("회원가입_실패");
    }
}
