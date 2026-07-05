package com.frank.biblioteca.favorite.application.service;

import java.util.UUID;

import com.frank.biblioteca.favorite.domain.model.Favorite;
import com.frank.biblioteca.favorite.domain.repository.FavoriteRepository;

public class RemoveFavoriteUseCase {
    private final FavoriteRepository favoriteRepository;

    public RemoveFavoriteUseCase(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public void execute(UUID favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId)
            .orElseThrow(() -> new IllegalArgumentException("Favorite not found with ID: " + favoriteId));
        favorite.setInactive();
        favoriteRepository.save(favorite);
    }
}
