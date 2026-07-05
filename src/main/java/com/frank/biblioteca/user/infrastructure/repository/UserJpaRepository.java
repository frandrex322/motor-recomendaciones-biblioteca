package com.frank.biblioteca.user.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frank.biblioteca.user.infrastructure.entity.UserEntity;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByName(String name);
    List<UserEntity> findByStatus(String status);
    List<UserEntity> findByRole(String role);
}
