package com.kimtaehoonki.account.presentation.dto.request;

import com.kimtaehoonki.account.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRegisterRequestDto {
    private String username;
    private String name;
    private String email;
    private String phoneNum;
    private String password;

    public Member makeMember() {
        return new Member(null,
            this.getUsername(), this.getName(), this.getEmail(),
            this.getPhoneNum(), this.getPassword());
    }
}
