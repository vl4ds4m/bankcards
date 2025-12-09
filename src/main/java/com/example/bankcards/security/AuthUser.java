package com.example.bankcards.security;

import com.example.bankcards.entity.User;
import com.example.bankcards.openapi.model.UserRole;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class AuthUser implements UserDetails {

    @Getter
    private final long id;

    private final String login;

    private final UserRole role;

    private final Set<GrantedAuthority> authorities;

    public AuthUser(User user) {
        id = user.getId();
        login = user.getLogin();
        role = user.getRole();
        authorities = Set.of(new SimpleGrantedAuthority("ROLE_" + role.getValue()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return login;
    }

    public boolean isAdmin() {
        return UserRole.ADMIN.equals(role);
    }

}
