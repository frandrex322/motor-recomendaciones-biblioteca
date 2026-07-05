package com.frank.biblioteca.review.domain.repository;

import java.util.List;
import java.util.UUID;

import com.frank.biblioteca.review.domain.model.Review;
import com.frank.biblioteca.review.domain.model.value_object.ReviewStatus;
import com.frank.biblioteca.utils.RepositoryGeneric;

public interface ReviewRepositoy extends RepositoryGeneric<UUID, Review> {
    List<Review> findByBookId(UUID bookId);
    List<Review> findByUserId(UUID userId);
    List<Review> findByStatus(ReviewStatus status);
}
