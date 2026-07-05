package com.frank.biblioteca.user.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.frank.biblioteca.user.domain.model.UserModel;
import com.frank.biblioteca.user.domain.repository.UserRepository;
import com.frank.biblioteca.user.infrastructure.mapper.UserMapper;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(UserModel entity) {
        jpaRepository.save(UserMapper.toEntity(entity));
    }

    @Override
    public Optional<UserModel> findById(UUID id) {
        return jpaRepository.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<UserModel> findAll() {
        return jpaRepository.findAll().stream()
            .map(UserMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public Optional<UserModel> findByName(String name) {
        return jpaRepository.findByName(name).map(UserMapper::toDomain);
    }

    @Override
    public List<UserModel> findByStatus(String status) {
        return jpaRepository.findByStatus(status).stream()
            .map(UserMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserModel> findByRole(String role) {
        return jpaRepository.findByRole(role).stream()
            .map(UserMapper::toDomain)
            .collect(Collectors.toList());
    }
}
