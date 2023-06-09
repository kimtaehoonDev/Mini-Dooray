package com.kimtaehoonki.task.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberResponseDto {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String phoneNum;
    private MemberStatus status;
}
