package com.frank.biblioteca.user.application.service;

import com.frank.biblioteca.user.domain.factory.UserFactory;
import com.frank.biblioteca.user.domain.repository.UserRepository;

public class CreateUserUseCase {
    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void executeCreate(String name, String email, String password, String role){
        userRepository.save(UserFactory.getInstance(name, email, password, role));
    }
}
