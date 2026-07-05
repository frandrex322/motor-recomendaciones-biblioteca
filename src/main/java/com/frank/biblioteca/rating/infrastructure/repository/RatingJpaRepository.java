package com.frank.biblioteca.rating.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frank.biblioteca.rating.infrastructure.entity.RatingEntity;

public interface RatingJpaRepository extends JpaRepository<RatingEntity, UUID> {
    List<RatingEntity> findByBookId(UUID bookId);
    List<RatingEntity> findByUserId(UUID userId);
    List<RatingEntity> findByBookIdAndUserId(UUID bookId, UUID userId);
}
