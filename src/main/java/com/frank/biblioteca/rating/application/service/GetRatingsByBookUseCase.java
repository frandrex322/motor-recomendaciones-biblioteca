package com.frank.biblioteca.rating.application.service;

import java.util.List;
import java.util.UUID;

import com.frank.biblioteca.rating.domain.model.Rate;
import com.frank.biblioteca.rating.domain.repository.ScoreRepository;

public class GetRatingsByBookUseCase {
    private final ScoreRepository scoreRepository;

    public GetRatingsByBookUseCase(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public List<Rate> execute(UUID bookId) {
        return scoreRepository.findByBookId(bookId);
    }
}
