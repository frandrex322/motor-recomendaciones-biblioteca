package com.frank.biblioteca.favorite.application.service;

import java.util.List;
import java.util.UUID;

import com.frank.biblioteca.favorite.domain.model.Favorite;
import com.frank.biblioteca.favorite.domain.repository.FavoriteRepository;

public class GetFavoritesByUserUseCase {
    private final FavoriteRepository favoriteRepository;

    public GetFavoritesByUserUseCase(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public List<Favorite> execute(UUID userId) {
        return favoriteRepository.findByUserId(userId);
    }
}
