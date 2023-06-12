package com.kimtaehoonki.gateway.web.service;

import com.kimtaehoonki.gateway.web.controller.MemberRegisterRequestDto;
import com.kimtaehoonki.gateway.web.exception.MemberRegisterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;


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
            throw new MemberRegisterException(e.getMessage());
        }

        throw new MemberRegisterException("member register failure");
    }
}
