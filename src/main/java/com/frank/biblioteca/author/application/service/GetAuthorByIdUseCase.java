package com.frank.biblioteca.author.application.service;

import java.util.UUID;

import com.frank.biblioteca.author.domain.model.Author;
import com.frank.biblioteca.author.domain.repository.AuthorRepository;

public class GetAuthorByIdUseCase {
    private final AuthorRepository authorRepository;

    public GetAuthorByIdUseCase(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author execute(UUID id) {
        return authorRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + id));
    }
}
