package com.example.bankcards.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    public long id() {
        return details().getId();
    }

    public boolean admin() {
        return details().isAdmin();
    }

    private static AuthUser details() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            var authUser = (AuthUser) auth.getPrincipal();
            if (authUser != null) {
                return authUser;
            }
        }
        throw new RuntimeException("Пользователь не авторизован");
    }

}
