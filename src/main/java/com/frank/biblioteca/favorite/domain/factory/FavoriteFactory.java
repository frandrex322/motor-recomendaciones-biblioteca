package com.frank.biblioteca.favorite.domain.factory;

import java.time.LocalDateTime;
import java.util.UUID;

import com.frank.biblioteca.favorite.domain.model.Favorite;
import com.frank.biblioteca.favorite.domain.model.value_object.FavoriteStatus;

public class FavoriteFactory {
    public static Favorite getInstance(UUID user, UUID book) {
        return new Favorite(UUID.randomUUID(), user, book, LocalDateTime.now(), FavoriteStatus.ACTIVE);
    }
}
