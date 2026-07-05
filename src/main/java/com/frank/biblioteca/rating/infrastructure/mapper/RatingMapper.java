package com.frank.biblioteca.rating.infrastructure.mapper;

import com.frank.biblioteca.rating.domain.factory.RatingFactory;
import com.frank.biblioteca.rating.domain.model.Rate;
import com.frank.biblioteca.rating.domain.model.value_object.Score;
import com.frank.biblioteca.rating.infrastructure.entity.RatingEntity;

public class RatingMapper {

    public static RatingEntity toEntity(Rate domain) {
        return new RatingEntity(domain.getId(), domain.getUserId(), domain.getBookId(),
            domain.getScore().getScore(), domain.getCreatedAt());
    }

    public static Rate toDomain(RatingEntity entity) {
        return RatingFactory.getInstance(entity.getUserId(), entity.getBookId(), new Score(entity.getScore()));
    }
}
