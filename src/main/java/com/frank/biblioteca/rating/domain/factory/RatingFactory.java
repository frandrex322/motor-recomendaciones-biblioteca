package com.frank.biblioteca.rating.domain.factory;

import java.util.UUID;

import com.frank.biblioteca.rating.domain.model.Rate;
import com.frank.biblioteca.rating.domain.model.value_object.Score;

public class RatingFactory {
    
    public static Rate getInstance(UUID userId, UUID bookId, Score score) {
        return new Rate(userId, bookId, score);
    } 
}
