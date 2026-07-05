package com.frank.biblioteca.book.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frank.biblioteca.book.infrastructure.entity.BookEntity;

public interface BookJpaRepository extends JpaRepository<BookEntity, UUID> {
    List<BookEntity> findByAuthorId(UUID authorId);
    List<BookEntity> findByStatus(String status);
}
