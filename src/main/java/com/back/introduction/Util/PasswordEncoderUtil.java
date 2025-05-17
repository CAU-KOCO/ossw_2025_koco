package com.back.introduction.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderUtil {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 密码加密
    public static String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // 密码比对
    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
