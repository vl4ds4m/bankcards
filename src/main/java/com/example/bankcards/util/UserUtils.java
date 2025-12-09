package com.example.bankcards.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserUtils {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserUtils() {
        throw new AssertionError();
    }

    public static String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean passwordsMatch(String raw, String encoded) {
        return passwordEncoder.matches(raw, encoded);
    }

}
