package com.kimtaehoonki.account.application;

import com.kimtaehoonki.account.application.dto.AuthInfo;
import com.kimtaehoonki.account.application.dto.AuthInfoServiceResponseDto;
import com.kimtaehoonki.account.domain.MemberRepository;
import com.kimtaehoonki.account.exception.impl.UserEmailDuplicateException;
import com.kimtaehoonki.account.exception.impl.UserNotFoundException;
import com.kimtaehoonki.account.exception.impl.UserNotMatchException;
import com.kimtaehoonki.account.exception.impl.UsernameDuplicateException;
import com.kimtaehoonki.account.presentation.dto.MemberRegisterRequestDto;
import com.kimtaehoonki.account.presentation.dto.response.MemberInfo;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
    @Mock
    MemberRepository memberRepository;

    MemberService memberService;

    @BeforeEach
    void init() {
        memberService = new MemberServiceImpl(memberRepository);
    }

    @Test
    void 유저정보를_가져온다() {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setId(1);
        memberInfo.setUsername("id1");
        memberInfo.setName("kim");
        memberInfo.setEmail("kim11@naver.com");
        memberInfo.setPhoneNum("010-1234-1234");

        Mockito.when(memberRepository.findMemberById(1)).thenReturn(Optional.of(memberInfo));

        MemberInfo result = memberService.findMember(1);

        Assertions.assertThat(result.getUsername()).isEqualTo(memberInfo.getUsername());
    }

    @Test
    void 유저정보가_없으면_예외를_반환한다() {
        Mockito.when(memberRepository.findMemberById(1)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> memberService.findMember(1))
            .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void 존재하는_아이디와_알맞은_패스워드로_정보를_가져온다() {
        AuthInfoServiceResponseDto dto = new AuthInfoServiceResponseDto("kim", "12345");
        Mockito.when(memberRepository.findByUsername("kim", AuthInfoServiceResponseDto.class))
            .thenReturn(Optional.of(dto));

        AuthInfo info = memberService.getAuthInfo("kim", "12345");
        Assertions.assertThat(info.getMemberId()).isEqualTo("kim");
    }

    @Test
    void 존재하지_않는_아이디로_조회시_예외를_반환한다() {
        Mockito.when(memberRepository.findByUsername("kim", AuthInfoServiceResponseDto.class))
            .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> memberService.getAuthInfo("kim", "12345"))
            .isInstanceOf(UserNotMatchException.class);
    }

    @Test
    void 패스워드가_틀리면_예외를_반환한다() {
        AuthInfoServiceResponseDto dto = new AuthInfoServiceResponseDto("kim", "12345");
        Mockito.when(memberRepository.findByUsername("kim", AuthInfoServiceResponseDto.class))
            .thenReturn(Optional.of(dto));

        Assertions.assertThatThrownBy(() -> memberService.getAuthInfo("kim", "nonono"))
            .isInstanceOf(UserNotMatchException.class);
    }

    @Test
    void 회원가입을_한다() {
        MemberRegisterRequestDto dto = new MemberRegisterRequestDto();
        dto.setUsername("id1");
        dto.setName("kim");
        dto.setEmail("kim11@naver.com");
        dto.setPhoneNum("010-1234-1234");
        dto.setPassword("1234");

        Mockito.when(memberRepository.existsByUsername("id1")).thenReturn(false);
        Mockito.when(memberRepository.existsByEmail("kim11@naver.com")).thenReturn(false);

        // 호출되었는지 확인한다
        Assertions.assertThat(memberService.register(dto)).isNull();
    }

    @Test
    void 중복_아이디로_회원가입시_예외를_반환한다() {
        MemberRegisterRequestDto dto = new MemberRegisterRequestDto();
        dto.setUsername("id1");
        dto.setName("kim");
        dto.setEmail("kim11@naver.com");
        dto.setPhoneNum("010-1234-1234");
        dto.setPassword("1234");

        Mockito.when(memberRepository.existsByUsername("id1")).thenReturn(true);

        Assertions.assertThatThrownBy(() -> memberService.register(dto)).isInstanceOf(
            UsernameDuplicateException.class);
    }

    @Test
    void 중복_이메일로_회원가입시_예외를_반환한다() {
        MemberRegisterRequestDto dto = new MemberRegisterRequestDto();
        dto.setUsername("id1");
        dto.setName("kim");
        dto.setEmail("kim11@naver.com");
        dto.setPhoneNum("010-1234-1234");
        dto.setPassword("1234");

        Mockito.when(memberRepository.existsByUsername("id1")).thenReturn(false);
        Mockito.when(memberRepository.existsByEmail("kim11@naver.com")).thenReturn(true);

        Assertions.assertThatThrownBy(() -> memberService.register(dto)).isInstanceOf(
            UserEmailDuplicateException.class);
    }
}