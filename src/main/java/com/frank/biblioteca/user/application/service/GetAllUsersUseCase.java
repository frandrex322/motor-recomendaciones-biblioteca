package com.frank.biblioteca.user.application.service;

import java.util.List;

import com.frank.biblioteca.user.domain.model.UserModel;
import com.frank.biblioteca.user.domain.repository.UserRepository;

public class GetAllUsersUseCase {
    private final UserRepository userRepository;

    public GetAllUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> execute() {
        return userRepository.findAll();
    }
}
