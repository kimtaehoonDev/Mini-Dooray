package com.kimtaehoonki.account.presentation.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberInfo {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String phoneNum;
}
