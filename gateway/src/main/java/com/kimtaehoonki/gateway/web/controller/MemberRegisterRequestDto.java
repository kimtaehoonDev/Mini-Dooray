package com.kimtaehoonki.gateway.web.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequestDto {
    private String username;
    private String password;
    private String name;
    private String email;
    private String phoneNum;
}
