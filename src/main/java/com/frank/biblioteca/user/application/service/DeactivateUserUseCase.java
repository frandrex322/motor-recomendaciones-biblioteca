package com.frank.biblioteca.user.application.service;

import java.util.UUID;

import com.frank.biblioteca.user.domain.model.UserModel;
import com.frank.biblioteca.user.domain.repository.UserRepository;

public class DeactivateUserUseCase {
    private final UserRepository userRepository;

    public DeactivateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID userId) {
        UserModel user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        user.setInactive();
        userRepository.save(user);
    }
}
