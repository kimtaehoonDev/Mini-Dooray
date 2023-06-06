package com.kimtaehoonki.gateway.security.dto;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MemberSecurityDto extends User {
    private Integer id;
    private String password;

    public MemberSecurityDto(String username, String password,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = Integer.valueOf(username);
        this.password = password;
    }
}
