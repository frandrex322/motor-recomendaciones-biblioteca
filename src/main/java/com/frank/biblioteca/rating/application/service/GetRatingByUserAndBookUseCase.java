package com.frank.biblioteca.rating.application.service;

import java.util.List;
import java.util.UUID;

import com.frank.biblioteca.rating.domain.model.Rate;
import com.frank.biblioteca.rating.domain.repository.ScoreRepository;

public class GetRatingByUserAndBookUseCase {
    private final ScoreRepository scoreRepository;

    public GetRatingByUserAndBookUseCase(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public Rate execute(UUID userId, UUID bookId) {
        List<Rate> ratings = scoreRepository.findByBookIdAndUserId(bookId, userId);
        if (ratings.isEmpty()) {
            throw new IllegalArgumentException("Rating not found for user " + userId + " and book " + bookId);
        }
        return ratings.get(0);
    }
}
