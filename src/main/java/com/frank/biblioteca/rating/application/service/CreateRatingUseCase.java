package com.frank.biblioteca.rating.application.service;

import java.util.UUID;

import com.frank.biblioteca.rating.domain.factory.RatingFactory;
import com.frank.biblioteca.rating.domain.model.value_object.Score;
import com.frank.biblioteca.rating.domain.repository.ScoreRepository;

public class CreateRatingUseCase {
    private final ScoreRepository scoreRepository;

    public CreateRatingUseCase(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public void execute(UUID userId, UUID bookId, int scoreValue) {
        scoreRepository.save(RatingFactory.getInstance(userId, bookId, new Score(scoreValue)));
    }
}
