package com.kimtaehoonki.gateway.web.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequestDto {
    private String username;
    private String password;
    private String name;
    private String email;
    private String phoneNum;

    public void setPasswordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
}
