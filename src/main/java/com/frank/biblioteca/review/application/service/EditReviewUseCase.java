package com.frank.biblioteca.review.application.service;

import java.util.UUID;

import com.frank.biblioteca.review.domain.model.Review;
import com.frank.biblioteca.review.domain.repository.ReviewRepositoy;

public class EditReviewUseCase {
    private final ReviewRepositoy reviewRepository;

    public EditReviewUseCase(ReviewRepositoy reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void execute(UUID reviewId, String newComment) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("Review not found with ID: " + reviewId));
        review.editComment(newComment);
        reviewRepository.save(review);
    }
}
