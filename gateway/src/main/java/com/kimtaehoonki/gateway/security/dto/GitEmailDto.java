package com.kimtaehoonki.gateway.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GitEmailDto {
    private String email;
    private Boolean primary;
    private Boolean verified;
    private String visibility;
}
