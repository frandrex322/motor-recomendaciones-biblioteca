package com.frank.biblioteca.book.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.frank.biblioteca.author.infrastructure.entity.AuthorEntity;
import com.frank.biblioteca.author.infrastructure.repository.AuthorJpaRepository;
import com.frank.biblioteca.book.domain.model.BookModel;
import com.frank.biblioteca.book.domain.repository.BookRepository;
import com.frank.biblioteca.book.infrastructure.mapper.BookMapper;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final BookJpaRepository jpaRepository;
    private final AuthorJpaRepository authorJpaRepository;

    public BookRepositoryImpl(BookJpaRepository jpaRepository, AuthorJpaRepository authorJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.authorJpaRepository = authorJpaRepository;
    }

    @Override
    public void save(BookModel entity) {
        jpaRepository.save(BookMapper.toEntity(entity));
    }

    @Override
    public Optional<BookModel> findById(UUID id) {
        return jpaRepository.findById(id).map(BookMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<BookModel> findAll() {
        return jpaRepository.findAll().stream()
            .map(BookMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<BookModel> findByAuthorName(String authorName) {
        AuthorEntity authorEntity = authorJpaRepository.findByFullName(authorName);
        if (authorEntity == null) return List.of();
        return jpaRepository.findByAuthorId(authorEntity.getId()).stream()
            .map(BookMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<BookModel> findByStatus(String status) {
        return jpaRepository.findByStatus(status).stream()
            .map(BookMapper::toDomain)
            .collect(Collectors.toList());
    }
}
