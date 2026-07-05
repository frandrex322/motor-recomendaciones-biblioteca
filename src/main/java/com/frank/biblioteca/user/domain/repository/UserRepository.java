package com.frank.biblioteca.user.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.frank.biblioteca.user.domain.model.UserModel;
import com.frank.biblioteca.utils.RepositoryGeneric;

public interface UserRepository extends RepositoryGeneric<UUID, UserModel> {
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByName(String name);
    List<UserModel> findByStatus(String status);
    List<UserModel> findByRole(String role);
}
