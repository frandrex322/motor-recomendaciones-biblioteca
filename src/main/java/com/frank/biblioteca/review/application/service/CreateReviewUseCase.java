package com.frank.biblioteca.review.application.service;

import java.util.UUID;

import com.frank.biblioteca.review.domain.factory.ReviewFactory;
import com.frank.biblioteca.review.domain.repository.ReviewRepositoy;

public class CreateReviewUseCase {
    private final ReviewRepositoy reviewRepository;

    public CreateReviewUseCase(ReviewRepositoy reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void execute(UUID userId, UUID bookId, String comment) {
        reviewRepository.save(ReviewFactory.getInstance(userId, bookId, comment));
    }
}
