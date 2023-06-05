package com.kimtaehoonki.account.application;

import static org.mockito.ArgumentMatchers.any;

import com.kimtaehoonki.account.application.dto.response.AuthInfo;
import com.kimtaehoonki.account.application.dto.response.AuthInfoServiceResponseDto;
import com.kimtaehoonki.account.domain.Member;
import com.kimtaehoonki.account.domain.MemberRepository;
import com.kimtaehoonki.account.exception.impl.UserEmailDuplicateException;
import com.kimtaehoonki.account.exception.impl.UserNotFoundException;
import com.kimtaehoonki.account.exception.impl.UserNotMatchException;
import com.kimtaehoonki.account.exception.impl.UsernameDuplicateException;
import com.kimtaehoonki.account.presentation.dto.request.MemberRegisterRequestDto;
import com.kimtaehoonki.account.presentation.dto.response.MemberInfo;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
    void 존재하는_아이디와_알맞은_패스워드로_인증정보를_가져온다()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException,
        IllegalAccessException, NoSuchFieldException {
        Class<AuthInfoServiceResponseDto> clazz =
            AuthInfoServiceResponseDto.class;
        Constructor<AuthInfoServiceResponseDto> constructor =
            clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        AuthInfoServiceResponseDto dto = constructor.newInstance();

        Field memberIdField = clazz.getDeclaredField("memberId");
        memberIdField.setAccessible(true);
        memberIdField.set(dto, 1);

        Field passwordField = clazz.getDeclaredField("password");
        passwordField.setAccessible(true);
        passwordField.set(dto,"12345");

        Mockito.when(memberRepository.findByUsername("kim", AuthInfoServiceResponseDto.class))
            .thenReturn(Optional.of(dto));

        AuthInfo info = memberService.getAuthInfo("kim", "12345");
        Assertions.assertThat(info.getMemberId()).isEqualTo(1);
    }

    @Test
    void 인증시_존재하지_않는_아이디로_조회시_예외를_반환한다() {
        Mockito.when(memberRepository.findByUsername("kim", AuthInfoServiceResponseDto.class))
            .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> memberService.getAuthInfo("kim", "12345"))
            .isInstanceOf(UserNotMatchException.class);
    }

    @Test
    void 인증시_패스워드가_틀리면_예외를_반환한다()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException,
        IllegalAccessException, NoSuchFieldException {
        Class<AuthInfoServiceResponseDto> clazz =
            AuthInfoServiceResponseDto.class;
        Constructor<AuthInfoServiceResponseDto> constructor =
            clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        AuthInfoServiceResponseDto dto = constructor.newInstance();

        Field memberIdField = clazz.getDeclaredField("memberId");
        memberIdField.setAccessible(true);
        memberIdField.set(dto, 1);

        Field passwordField = clazz.getDeclaredField("password");
        passwordField.setAccessible(true);
        passwordField.set(dto,"12345");

        Mockito.when(memberRepository.findByUsername("kim", AuthInfoServiceResponseDto.class))
            .thenReturn(Optional.of(dto));

        Assertions.assertThatThrownBy(() -> memberService.getAuthInfo("kim", "nonono"))
            .isInstanceOf(UserNotMatchException.class);
    }

    @Test
    void 회원가입을_한다() throws NoSuchMethodException, InvocationTargetException, InstantiationException,
        IllegalAccessException, NoSuchFieldException {
        Class<MemberRegisterRequestDto> clazz =
            MemberRegisterRequestDto.class;
        Constructor<MemberRegisterRequestDto> constructor =
            clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        MemberRegisterRequestDto dto = constructor.newInstance();

        Field usernameField = clazz.getDeclaredField("username");
        usernameField.setAccessible(true);
        usernameField.set(dto, "id1");

        Field emailField = clazz.getDeclaredField("email");
        emailField.setAccessible(true);
        emailField.set(dto, "kim11@naver.com");

        Mockito.when(memberRepository.existsByUsername("id1")).thenReturn(false);
        Mockito.when(memberRepository.existsByEmail("kim11@naver.com")).thenReturn(false);
        Mockito.when(memberRepository.save(any()))
            .thenReturn(new Member(10, null, null, null, null, null));

        Integer registerMemberId = memberService.register(dto);
        Assertions.assertThat(registerMemberId).isEqualTo(10);
    }

    @Test
    void 중복_아이디로_회원가입시_예외를_반환한다()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException,
        IllegalAccessException, NoSuchFieldException {
        Class<MemberRegisterRequestDto> clazz =
            MemberRegisterRequestDto.class;
        Constructor<MemberRegisterRequestDto> constructor =
            clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        MemberRegisterRequestDto dto = constructor.newInstance();

        Field usernameField = clazz.getDeclaredField("username");
        usernameField.setAccessible(true);
        usernameField.set(dto, "id1");

        Field emailField = clazz.getDeclaredField("email");
        emailField.setAccessible(true);
        emailField.set(dto, "kim11@naver.com");

        Mockito.when(memberRepository.existsByUsername("id1")).thenReturn(true);

        Assertions.assertThatThrownBy(() -> memberService.register(dto)).isInstanceOf(
            UsernameDuplicateException.class);
    }

    @Test
    void 중복_이메일로_회원가입시_예외를_반환한다()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException,
        IllegalAccessException, NoSuchFieldException {
        Class<MemberRegisterRequestDto> clazz =
            MemberRegisterRequestDto.class;
        Constructor<MemberRegisterRequestDto> constructor =
            clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        MemberRegisterRequestDto dto = constructor.newInstance();

        Field usernameField = clazz.getDeclaredField("username");
        usernameField.setAccessible(true);
        usernameField.set(dto, "id1");

        Field emailField = clazz.getDeclaredField("email");
        emailField.setAccessible(true);
        emailField.set(dto, "kim11@naver.com");

        Mockito.when(memberRepository.existsByUsername("id1")).thenReturn(false);
        Mockito.when(memberRepository.existsByEmail("kim11@naver.com")).thenReturn(true);

        Assertions.assertThatThrownBy(() -> memberService.register(dto)).isInstanceOf(
            UserEmailDuplicateException.class);
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
}