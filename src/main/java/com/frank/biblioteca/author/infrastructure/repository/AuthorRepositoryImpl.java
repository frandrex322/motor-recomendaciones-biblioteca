package com.frank.biblioteca.author.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.frank.biblioteca.author.domain.model.Author;
import com.frank.biblioteca.author.domain.repository.AuthorRepository;
import com.frank.biblioteca.author.infrastructure.mapper.AuthorMapper;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorJpaRepository jpaRepository;

    public AuthorRepositoryImpl(AuthorJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Author entity) {
        jpaRepository.save(AuthorMapper.toEntity(entity));
    }

    @Override
    public Optional<Author> findById(UUID id) {
        return jpaRepository.findById(id).map(AuthorMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Author> findAll() {
        return jpaRepository.findAll().stream()
            .map(AuthorMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public Author findByFullName(String fullName) {
        var entity = jpaRepository.findByFullName(fullName);
        return entity != null ? AuthorMapper.toDomain(entity) : null;
    }

    @Override
    public List<Author> findByNationality(String nationality) {
        return jpaRepository.findByNationality(nationality).stream()
            .map(AuthorMapper::toDomain)
            .collect(Collectors.toList());
    }
}
