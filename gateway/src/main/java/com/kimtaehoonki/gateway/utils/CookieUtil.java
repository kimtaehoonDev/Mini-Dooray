package com.kimtaehoonki.gateway.utils;

import java.util.Arrays;
import java.util.Base64;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

public class CookieUtil {
    // 요청 값(이름, 값, 만료기간)을 바탕으로 쿠키 추가
    public static void addCookie(HttpServletResponse resp, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        resp.addCookie(cookie);
    }

    // 쿠키의 이름을 입력받아 쿠키 삭제
    public static void deleteCookie(HttpServletRequest req, HttpServletResponse resp, String name) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return;
        }

        Arrays.stream(cookies)
                .filter(cookie -> name.equals(cookie.getName()))
                .forEach(cookie -> {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                });
    }

    // 객체를 직렬화해 쿠키의 값으로 변환
    public static String serialize(Object obj) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    // 쿠키를 역직렬화해 객체로 변환
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }
}
