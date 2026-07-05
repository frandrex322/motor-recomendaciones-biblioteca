package com.frank.biblioteca.review.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.frank.biblioteca.review.domain.model.Review;
import com.frank.biblioteca.review.domain.model.value_object.ReviewStatus;
import com.frank.biblioteca.review.domain.repository.ReviewRepositoy;
import com.frank.biblioteca.review.infrastructure.mapper.ReviewMapper;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoy {

    private final ReviewJpaRepository jpaRepository;

    public ReviewRepositoryImpl(ReviewJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Review entity) {
        jpaRepository.save(ReviewMapper.toEntity(entity));
    }

    @Override
    public Optional<Review> findById(UUID id) {
        return jpaRepository.findById(id).map(ReviewMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Review> findAll() {
        return jpaRepository.findAll().stream()
            .map(ReviewMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Review> findByBookId(UUID bookId) {
        return jpaRepository.findByBookId(bookId).stream()
            .map(ReviewMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Review> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).stream()
            .map(ReviewMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Review> findByStatus(ReviewStatus status) {
        return jpaRepository.findByStatus(status.name()).stream()
            .map(ReviewMapper::toDomain)
            .collect(Collectors.toList());
    }
}
