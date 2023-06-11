package com.kimtaehoonki.gateway.security.dto;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@ToString
public class MemberSecurityDto extends User implements OAuth2User {
    private Integer id;
    private String username;
    private String password;
    private Map<String, Object> props; // 소셜 로그인 정보

    public MemberSecurityDto(String username, String password,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.username = username;
        this.password = password;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.getProps();
    }

    @Override
    public String getName() {
        return this.username;
    }
}
