package com.frank.biblioteca.favorite.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frank.biblioteca.favorite.infrastructure.entity.FavoriteEntity;

public interface FavoriteJpaRepository extends JpaRepository<FavoriteEntity, UUID> {
    List<FavoriteEntity> findByUserId(UUID userId);
    List<FavoriteEntity> findByBookId(UUID bookId);
}
