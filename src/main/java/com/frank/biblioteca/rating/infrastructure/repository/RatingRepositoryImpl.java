package com.frank.biblioteca.rating.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.frank.biblioteca.rating.domain.model.Rate;
import com.frank.biblioteca.rating.domain.repository.ScoreRepository;
import com.frank.biblioteca.rating.infrastructure.mapper.RatingMapper;

@Repository
public class RatingRepositoryImpl implements ScoreRepository {

    private final RatingJpaRepository jpaRepository;

    public RatingRepositoryImpl(RatingJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Rate entity) {
        jpaRepository.save(RatingMapper.toEntity(entity));
    }

    @Override
    public Optional<Rate> findById(UUID id) {
        return jpaRepository.findById(id).map(RatingMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Rate> findAll() {
        return jpaRepository.findAll().stream()
            .map(RatingMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Rate> findByBookId(UUID bookId) {
        return jpaRepository.findByBookId(bookId).stream()
            .map(RatingMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Rate> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).stream()
            .map(RatingMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Rate> findByBookIdAndUserId(UUID bookId, UUID userId) {
        return jpaRepository.findByBookIdAndUserId(bookId, userId).stream()
            .map(RatingMapper::toDomain)
            .collect(Collectors.toList());
    }
}
