package com.frank.biblioteca.review.application.service;

import java.util.UUID;

import com.frank.biblioteca.review.domain.model.Review;
import com.frank.biblioteca.review.domain.repository.ReviewRepositoy;

public class DeleteReviewUseCase {
    private final ReviewRepositoy reviewRepository;

    public DeleteReviewUseCase(ReviewRepositoy reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void execute(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("Review not found with ID: " + reviewId));
        review.setDeleted();
        reviewRepository.save(review);
    }
}
