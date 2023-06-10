package com.kimtaehoonki.gateway.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthInfoResponseDto {
    private Integer id;
    private String password;
    private String status;
    private String authority;
}
