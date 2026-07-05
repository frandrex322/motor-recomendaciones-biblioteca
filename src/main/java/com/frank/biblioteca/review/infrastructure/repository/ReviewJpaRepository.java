package com.frank.biblioteca.review.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frank.biblioteca.review.infrastructure.entity.ReviewEntity;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, UUID> {
    List<ReviewEntity> findByBookId(UUID bookId);
    List<ReviewEntity> findByUserId(UUID userId);
    List<ReviewEntity> findByStatus(String status);
}
