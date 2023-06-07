package com.kimtaehoonki.task.member;

import com.kimtaehoonki.task.exception.impl.MemberNotFoundException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class AccountRestTemplate {
    public static final String ACCOUNT_SERVER_URL = "http://localhost:9091";

    private final RestTemplate rt;

    public void validateMemberExists(int memberId) {
        URI uri = UriComponentsBuilder.fromUriString(ACCOUNT_SERVER_URL)
            .path("/members/{id}")
            .build()
            .expand(memberId)
            .toUri();

        try {
            ResponseEntity<MemberResponseDto> response = rt.getForEntity(uri, MemberResponseDto.class);
            HttpStatus statusCode = response.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                return;
            }
            throw new RuntimeException("예상하지 못한 응답코드입니다");
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new MemberNotFoundException();
            }
        } catch (HttpServerErrorException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
