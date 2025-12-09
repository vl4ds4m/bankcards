package com.example.bankcards.service;

import com.example.bankcards.entity.User;
import com.example.bankcards.exception.InvalidRequestException;
import com.example.bankcards.openapi.model.AuthRequest;
import com.example.bankcards.openapi.model.AuthResponse;
import com.example.bankcards.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final JwtService jwtService;

    public AuthResponse authenticate(AuthRequest request) {
        User user = userService.getUserByLogin(request.getLogin());
        if (!UserUtils.passwordsMatch(request.getPassword(), user.getPassword())) {
            throw new InvalidRequestException("Неверный пароль.");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

}
