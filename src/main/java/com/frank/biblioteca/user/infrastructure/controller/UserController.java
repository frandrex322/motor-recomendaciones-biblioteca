package com.frank.biblioteca.user.infrastructure.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.frank.biblioteca.user.domain.factory.UserFactory;
import com.frank.biblioteca.user.domain.model.UserModel;
import com.frank.biblioteca.user.domain.repository.UserRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel create(@Valid @RequestBody CreateUserRequest request) {
        if (userRepository.findByEmail(request.email).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (userRepository.findByName(request.name).isPresent()) {
            throw new IllegalArgumentException("El nombre ya está registrado");
        }
        UserModel user = UserFactory.getInstance(request.name, request.email, request.password, request.role);
        userRepository.save(user);
        return user;
    }

    @GetMapping
    public List<UserModel> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public UserModel getById(@PathVariable UUID id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/email/{email}")
    public UserModel findByEmail(@PathVariable String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PatchMapping("/{id}/deactivate")
    public UserModel deactivate(@PathVariable UUID id) {
        UserModel user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setInactive();
        userRepository.save(user);
        return user;
    }

    record CreateUserRequest(
        @NotBlank(message = "El nombre es obligatorio") String name,
        @NotBlank @Email(message = "Email inválido") String email,
        @NotBlank @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String password,
        @NotBlank(message = "El rol es obligatorio") String role
    ) {}
}
