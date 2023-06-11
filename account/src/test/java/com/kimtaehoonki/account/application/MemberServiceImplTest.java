package com.kimtaehoonki.account.application;

import static org.mockito.ArgumentMatchers.any;

import com.kimtaehoonki.account.application.dto.response.AuthInfo;
import com.kimtaehoonki.account.domain.Authority;
import com.kimtaehoonki.account.domain.Member;
import com.kimtaehoonki.account.domain.MemberRepository;
import com.kimtaehoonki.account.domain.MemberStatus;
import com.kimtaehoonki.account.exception.impl.UserEmailDuplicateException;
import com.kimtaehoonki.account.exception.impl.UserNotFoundException;
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
            .thenReturn(new Member(10, null, null,
                null, null, null, null, null));

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
    void 멤버정보를_아이디로_가져온다() {
        MemberInfo memberInfo = new MemberInfo() {

            @Override
            public Integer getId() {
                return 1;
            }

            @Override
            public String getUsername() {
                return "id1";
            }

            @Override
            public String getName() {
                return "kim";
            }

            @Override
            public String getEmail() {
                return "kim11@naver.com";
            }

            @Override
            public String getPhoneNum() {
                return "010-1234-1234";
            }

            @Override
            public MemberStatus getStatus() {
                return MemberStatus.SUBSCRIPTION;
            }
        };

        Mockito.when(memberRepository.findById(1, MemberInfo.class)).thenReturn(Optional.of(memberInfo));
        MemberInfo result = memberService.findMember(1);
        Assertions.assertThat(result.getUsername()).isEqualTo(memberInfo.getUsername());
    }

    @Test
    void 멤버정보가_없으면_예외를_반환한다() {
        Mockito.when(memberRepository.findById(1, MemberInfo.class)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> memberService.findMember(1))
            .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void 멤버아이디로_인증정보를_가져온다() {
        AuthInfo authInfo = new AuthInfo() {

            @Override
            public Integer getId() {
                return 1;
            }

            @Override
            public String getPassword() {
                return "12345";
            }

            @Override
            public MemberStatus getStatus() {
                return null;
            }

            @Override
            public Authority getAuthority() {
                return null;
            }
        };

        Mockito.when(memberRepository.findByUsername("hello", AuthInfo.class)).thenReturn(Optional.of(authInfo));
        AuthInfo result = memberService.findMemberUsingUsername("hello");
        Assertions.assertThat(result.getId()).isEqualTo(1);
    }


    @Test
    void 멤버아이디로_조회시_존재하지_않는_멤버의_경우_예외를_반환한다() {
        Mockito.when(memberRepository.findByUsername("hello", AuthInfo.class)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> memberService.findMemberUsingUsername("hello"))
            .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void 이메일로로_인증정보를_가져온다() {
        AuthInfo authInfo = new AuthInfo() {

            @Override
            public Integer getId() {
                return 1;
            }

            @Override
            public String getPassword() {
                return "12345";
            }

            @Override
            public MemberStatus getStatus() {
                return null;
            }

            @Override
            public Authority getAuthority() {
                return null;
            }
        };

        Mockito.when(memberRepository.findByEmail("ds@naver.com")).thenReturn(Optional.of(authInfo));
        AuthInfo result = memberService.findMemberUsingEmail("ds@naver.com");
        Assertions.assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void 이메일로_조회시_존재하지_않는_멤버의_경우_예외를_반환한다() {
        Mockito.when(memberRepository.findByEmail("ds@naver.com")).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> memberService.findMemberUsingEmail("ds@naver.com"))
            .isInstanceOf(UserNotFoundException.class);
    }

}