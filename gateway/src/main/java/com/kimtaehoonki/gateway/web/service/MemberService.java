package com.kimtaehoonki.gateway.web.service;

import com.kimtaehoonki.gateway.web.controller.MemberRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final RestTemplate restTemplate;
    @Value("${kimteahoonki.accountapi.members.register}")
    private String registerUrl;

    public Integer register(MemberRegisterRequestDto requestDto) {
        ResponseEntity<MemberRegisterResponseDto> response =
                restTemplate.postForEntity(
                        registerUrl,
                        requestDto,
                        MemberRegisterResponseDto.class
                );

        log.info("response.status={}", response.getStatusCode().toString());
        if (response.getStatusCode().is2xxSuccessful()) {
            MemberRegisterResponseDto responseDto = response.getBody();
            return responseDto.getMemberId();
        }
        return null;
    }
}
