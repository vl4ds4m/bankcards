package com.example.bankcards.controller;

import com.example.bankcards.openapi.api.AuthApi;
import com.example.bankcards.openapi.model.AuthRequest;
import com.example.bankcards.openapi.model.AuthResponse;
import com.example.bankcards.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<AuthResponse> auth(AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }

}
