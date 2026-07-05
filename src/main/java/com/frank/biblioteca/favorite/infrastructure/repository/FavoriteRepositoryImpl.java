package com.frank.biblioteca.favorite.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.frank.biblioteca.favorite.domain.model.Favorite;
import com.frank.biblioteca.favorite.domain.repository.FavoriteRepository;
import com.frank.biblioteca.favorite.infrastructure.mapper.FavoriteMapper;

@Repository
public class FavoriteRepositoryImpl implements FavoriteRepository {

    private final FavoriteJpaRepository jpaRepository;

    public FavoriteRepositoryImpl(FavoriteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Favorite entity) {
        jpaRepository.save(FavoriteMapper.toEntity(entity));
    }

    @Override
    public Optional<Favorite> findById(UUID id) {
        return jpaRepository.findById(id).map(FavoriteMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Favorite> findAll() {
        return jpaRepository.findAll().stream()
            .map(FavoriteMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Favorite> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).stream()
            .map(FavoriteMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Favorite> findByBookId(UUID bookId) {
        return jpaRepository.findByBookId(bookId).stream()
            .map(FavoriteMapper::toDomain)
            .collect(Collectors.toList());
    }
}
