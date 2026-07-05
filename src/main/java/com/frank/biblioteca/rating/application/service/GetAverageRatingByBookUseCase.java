package com.frank.biblioteca.rating.application.service;

import java.util.List;
import java.util.UUID;

import com.frank.biblioteca.rating.domain.model.Rate;
import com.frank.biblioteca.rating.domain.repository.ScoreRepository;

public class GetAverageRatingByBookUseCase {
    private final ScoreRepository scoreRepository;

    public GetAverageRatingByBookUseCase(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public double execute(UUID bookId) {
        List<Rate> ratings = scoreRepository.findByBookId(bookId);
        if (ratings.isEmpty()) {
            return 0.0;
        }
        return ratings.stream()
            .mapToInt(r -> r.getScore().getScore())
            .average()
            .orElse(0.0);
    }
}
