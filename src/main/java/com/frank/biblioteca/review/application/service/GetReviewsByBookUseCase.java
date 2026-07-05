package com.frank.biblioteca.review.application.service;

import java.util.List;
import java.util.UUID;

import com.frank.biblioteca.review.domain.model.Review;
import com.frank.biblioteca.review.domain.repository.ReviewRepositoy;

public class GetReviewsByBookUseCase {
    private final ReviewRepositoy reviewRepository;

    public GetReviewsByBookUseCase(ReviewRepositoy reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> execute(UUID bookId) {
        return reviewRepository.findByBookId(bookId);
    }
}
