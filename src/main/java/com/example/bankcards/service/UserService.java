package com.example.bankcards.service;

import com.example.bankcards.entity.User;
import com.example.bankcards.exception.InvalidRequestException;
import com.example.bankcards.openapi.model.CreateUserRequest;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new InvalidRequestException("Пользователь '" + login + "' не существует."));
    }

    public long createUser(CreateUserRequest request) {
        String login = request.getLogin();
        if (login.isBlank()) {
            throw new InvalidRequestException("Логин пользователя должно быть непустой строкой.");
        }

        if (userRepository.existsByLogin(login)) {
            throw new InvalidRequestException("Пользователь '" + login + "' уже существует.");
        }

        String password = request.getPassword();
        if (password.isBlank()) {
            throw new InvalidRequestException("Пароль должен быть непустой строкой.");
        }

        var user = new User(login, UserUtils.encodePassword(password), request.getRole());
        user = userRepository.save(user);
        return user.getId();
    }

}
