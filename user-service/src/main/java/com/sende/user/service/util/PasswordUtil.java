package com.sende.user.service.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码加密/校验（BCrypt）
 */
public final class PasswordUtil {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private PasswordUtil() {}

    public static String encode(String raw) {
        if (raw == null) return null;
        return ENCODER.encode(raw);
    }

    public static boolean matches(String raw, String hashed) {
        if (raw == null || hashed == null) return false;
        return ENCODER.matches(raw, hashed);
    }
}