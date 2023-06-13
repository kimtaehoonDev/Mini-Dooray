package com.kimtaehoonki.account.presentation.dto.request;

import com.kimtaehoonki.account.domain.Authority;
import com.kimtaehoonki.account.domain.Member;
import com.kimtaehoonki.account.domain.MemberStatus;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRegisterRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    private String name;

    @Email
    private String email;

    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$",
        message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phoneNum;

    @NotBlank
    private String password;

    public Member makeMember() {
        return new Member(null,
            this.getUsername(), this.getName(), this.getEmail(),
            this.getPhoneNum(), this.getPassword(),
            MemberStatus.SUBSCRIPTION, Authority.USER);
    }
}
