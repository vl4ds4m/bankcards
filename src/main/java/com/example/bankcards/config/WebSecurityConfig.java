package com.example.bankcards.config;

import com.example.bankcards.openapi.model.UserRole;
import com.example.bankcards.security.AuthTokenFilter;
import com.example.bankcards.service.JwtService;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtService jwtService;

    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(cfg -> cfg
                        .requestMatchers(HttpMethod.GET, "/cards").authenticated()
                        .requestMatchers(HttpMethod.GET, "/cards/{id}/balance").hasRole(UserRole.USER.getValue())
                        .requestMatchers("/transfer").hasRole(UserRole.USER.getValue())
                        .requestMatchers(HttpMethod.PUT, "/cards/{id}/balance").hasRole(UserRole.ADMIN.getValue())
                        .requestMatchers("/users/**", "/cards/**").hasRole(UserRole.ADMIN.getValue())
                        .anyRequest().permitAll())
                .addFilterAt(new AuthTokenFilter(jwtService, userService), AuthenticationFilter.class)
                .build();
    }

}
