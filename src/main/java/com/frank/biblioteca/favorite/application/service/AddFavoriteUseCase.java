package com.frank.biblioteca.favorite.application.service;

import java.util.UUID;

import com.frank.biblioteca.favorite.domain.factory.FavoriteFactory;
import com.frank.biblioteca.favorite.domain.repository.FavoriteRepository;

public class AddFavoriteUseCase {
    private final FavoriteRepository favoriteRepository;

    public AddFavoriteUseCase(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public void execute(UUID userId, UUID bookId) {
        favoriteRepository.save(FavoriteFactory.getInstance(userId, bookId));
    }
}
