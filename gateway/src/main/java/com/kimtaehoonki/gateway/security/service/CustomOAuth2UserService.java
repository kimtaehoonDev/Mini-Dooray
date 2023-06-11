package com.kimtaehoonki.gateway.security.service;

import com.kimtaehoonki.gateway.security.dto.AuthInfoResponseDto;
import com.kimtaehoonki.gateway.security.dto.GitEmailDto;
import com.kimtaehoonki.gateway.security.dto.MemberSecurityDto;
import com.kimtaehoonki.gateway.security.exception.AuthenticationOAuth2UserNotFoundException;
import com.kimtaehoonki.gateway.security.exception.AuthenticationRestTemplateException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final RestTemplate restTemplate;
    @Value("${github.token}")
    private String githubToken;

    @Value("${github.email.url}")
    private String githubEmailUrl;

    @Value("${kimteahoonki.accountapi.members.authInfo}")
    private String authInfoUrl;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest:{}", userRequest.toString());

        try {
            /**
             * github email 가져오기
             */
            HttpHeaders httpHeaders = getGithubHttpHeaders();
            HttpEntity<Object> requestEntity = new HttpEntity<>(httpHeaders);

            ResponseEntity<List<GitEmailDto>> response =
                    restTemplate.exchange(
                            githubEmailUrl,
                            HttpMethod.GET,
                            requestEntity,
                            new ParameterizedTypeReference<>() {
                            });

            if (response.getStatusCode() == HttpStatus.OK) {
                return getMemberSecurityDto(response);
            }

        } catch (RestClientException e) {
            throw new AuthenticationRestTemplateException(e.getMessage(), e);
        }

        throw new RuntimeException();
    }

    private MemberSecurityDto getMemberSecurityDto(ResponseEntity<List<GitEmailDto>> response) {
        List<GitEmailDto> responseBody = response.getBody();

        String email = responseBody
                .stream()
                .filter(GitEmailDto::getPrimary)
                .findAny()
                .orElseThrow(IllegalAccessError::new)
                .getEmail();

        return generateDTO(email);
    }

    /**
     * 데이터베이스에 해당 이메일의 사용자가 없다면 회원추가
     * 데이터베이스에 해당 이메일의 사용자가 있다면 정보가져오기
     */
    private MemberSecurityDto generateDTO(String email) {
        String oauthEmailUri = UriComponentsBuilder
                .fromHttpUrl(authInfoUrl)
                .queryParam("email", email)
                .encode()
                .toUriString();

        try {
            ResponseEntity<AuthInfoResponseDto> response =
                    restTemplate.getForEntity(
                            oauthEmailUri,
                            AuthInfoResponseDto.class,
                            email);

            if (response.getStatusCode().is2xxSuccessful()) {
                AuthInfoResponseDto responseDto = response.getBody();

                return new MemberSecurityDto(
                        email,
                        responseDto.getPassword(),
                        Collections.singleton(
                                new SimpleGrantedAuthority("ROLE_" + responseDto.getAuthority()))
                );
            }

            // RestTemplate 통신 오류
        } catch (RestClientException e) {
            if (e.getMessage().startsWith("404")) {
                throw new AuthenticationOAuth2UserNotFoundException("회원가입이 필요합니다", email);
            }

            throw new AuthenticationRestTemplateException(e.getMessage(), e);
        }

        throw new RuntimeException();
    }


    private HttpHeaders getGithubHttpHeaders() {
        HttpHeaders httpHeaders = getDefaultHeaders();
        httpHeaders.setAccept(List.of(MediaType.valueOf("application/vnd.github+json")));
        httpHeaders.setBearerAuth(githubToken);
        httpHeaders.set("X-GibHub-Api-Version", "2022-11-28");
        return httpHeaders;
    }

    private static HttpHeaders getDefaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }
}
