package com.frank.biblioteca.review.infrastructure.mapper;

import com.frank.biblioteca.review.domain.factory.ReviewFactory;
import com.frank.biblioteca.review.domain.model.Review;
import com.frank.biblioteca.review.infrastructure.entity.ReviewEntity;

public class ReviewMapper {

    public static ReviewEntity toEntity(Review domain) {
        return new ReviewEntity(domain.getId(), domain.getUser(), domain.getBook(),
            domain.getComment(), domain.getStatus().name(), domain.getCreatedAt(), domain.getUpdatedAt());
    }

    public static Review toDomain(ReviewEntity entity) {
        Review review = ReviewFactory.getInstance(entity.getUserId(), entity.getBookId(), entity.getComment());
        if ("EDITED".equals(entity.getStatus())) review.editComment(entity.getComment());
        if ("DELETED".equals(entity.getStatus())) review.setDeleted();
        return review;
    }
}
