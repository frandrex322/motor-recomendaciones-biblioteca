package com.frank.biblioteca.favorite.domain.repository;

import java.util.List;
import java.util.UUID;

import com.frank.biblioteca.favorite.domain.model.Favorite;
import com.frank.biblioteca.utils.RepositoryGeneric;

public interface FavoriteRepository extends RepositoryGeneric<UUID, Favorite> {
    List<Favorite> findByUserId(UUID userId);
    List<Favorite> findByBookId(UUID bookId);
}
