package com.kimtaehoonki.gateway.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberIdPasswordResponseDto {
    private Integer id;
    private String password;
    private String authority;
}
