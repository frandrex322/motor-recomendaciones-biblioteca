package com.frank.biblioteca.author.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frank.biblioteca.author.infrastructure.entity.AuthorEntity;

public interface AuthorJpaRepository extends JpaRepository<AuthorEntity, UUID> {
    AuthorEntity findByFullName(String fullName);
    List<AuthorEntity> findByNationality(String nationality);
}
