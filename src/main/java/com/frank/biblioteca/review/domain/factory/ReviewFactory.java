package com.frank.biblioteca.review.domain.factory;

import java.util.UUID;

import com.frank.biblioteca.review.domain.model.Review;

public class ReviewFactory {
    
    public static Review getInstance(UUID user, UUID book, String comment) {
        return new Review(user, book, comment);
    }
}
