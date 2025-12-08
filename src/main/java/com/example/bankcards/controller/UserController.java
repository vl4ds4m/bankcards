package com.example.bankcards.controller;

import com.example.bankcards.openapi.api.UsersApi;
import com.example.bankcards.openapi.model.CreateUserRequest;
import com.example.bankcards.openapi.model.CreateUserResponse;
import com.example.bankcards.openapi.model.UserInfo;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final UserService userService;

    @Override
    public ResponseEntity<CreateUserResponse> createUser(CreateUserRequest createUserRequest) {
        long userId = userService.createUser(createUserRequest.getName());
        return ResponseEntity.ok(new CreateUserResponse(userId));
    }

    @Override
    public ResponseEntity<List<UserInfo>> getUsers() {
        List<UserInfo> body = userService.getUsers()
                .stream()
                .map(u -> new UserInfo(u.getId(), u.getName()))
                .toList();
        return ResponseEntity.ok(body);
    }

}
