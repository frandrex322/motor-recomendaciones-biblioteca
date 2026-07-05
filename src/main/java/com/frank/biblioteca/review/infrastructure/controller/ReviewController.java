package com.frank.biblioteca.review.infrastructure.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.frank.biblioteca.review.domain.factory.ReviewFactory;
import com.frank.biblioteca.review.domain.model.Review;
import com.frank.biblioteca.review.domain.repository.ReviewRepositoy;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewRepositoy reviewRepository;

    public ReviewController(ReviewRepositoy reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review create(@RequestBody CreateReviewRequest request) {
        Review review = ReviewFactory.getInstance(request.userId, request.bookId, request.comment);
        reviewRepository.save(review);
        return review;
    }

    @GetMapping("/book/{bookId}")
    public List<Review> findByBook(@PathVariable UUID bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    @PatchMapping("/{id}")
    public Review edit(@PathVariable UUID id, @RequestBody EditReviewRequest request) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found"));
        review.editComment(request.comment);
        reviewRepository.save(review);
        return review;
    }

    @PatchMapping("/{id}/delete")
    public Review delete(@PathVariable UUID id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setDeleted();
        reviewRepository.save(review);
        return review;
    }

    record CreateReviewRequest(UUID userId, UUID bookId, String comment) {}
    record EditReviewRequest(String comment) {}
}
