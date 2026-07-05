package com.frank.biblioteca.rating.domain.repository;

import java.util.List;
import java.util.UUID;

import com.frank.biblioteca.rating.domain.model.Rate;
import com.frank.biblioteca.utils.RepositoryGeneric;

public interface ScoreRepository extends RepositoryGeneric<UUID, Rate> {
    List<Rate> findByBookId(UUID bookId);
    List<Rate> findByUserId(UUID userId);
    List<Rate> findByBookIdAndUserId(UUID bookId, UUID userId);
}
