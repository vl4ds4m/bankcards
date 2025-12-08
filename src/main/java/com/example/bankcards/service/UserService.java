package com.example.bankcards.service;

import com.example.bankcards.entity.User;
import com.example.bankcards.exception.InvalidRequestException;
import com.example.bankcards.repository.UserRepository;
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

    public long createUser(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidRequestException("Имя пользователя должно быть непустой строкой.");
        }

        var user = new User(name);
        user = userRepository.save(user);
        return user.getId();
    }

}
