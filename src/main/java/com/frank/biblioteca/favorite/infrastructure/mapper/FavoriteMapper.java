package com.frank.biblioteca.favorite.infrastructure.mapper;

import com.frank.biblioteca.favorite.domain.factory.FavoriteFactory;
import com.frank.biblioteca.favorite.domain.model.Favorite;
import com.frank.biblioteca.favorite.domain.model.value_object.FavoriteStatus;
import com.frank.biblioteca.favorite.infrastructure.entity.FavoriteEntity;

public class FavoriteMapper {

    public static FavoriteEntity toEntity(Favorite domain) {
        return new FavoriteEntity(domain.getId(), domain.getUser(), domain.getBook(),
            domain.getStatus().name(), domain.getCreatedAt());
    }

    public static Favorite toDomain(FavoriteEntity entity) {
        Favorite favorite = FavoriteFactory.getInstance(entity.getUserId(), entity.getBookId());
        if ("INACTIVE".equals(entity.getStatus())) favorite.setInactive();
        return favorite;
    }
}
