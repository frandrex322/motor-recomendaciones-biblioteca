package com.frank.biblioteca.author.application.service;

import java.util.List;

import com.frank.biblioteca.author.domain.model.Author;
import com.frank.biblioteca.author.domain.repository.AuthorRepository;

public class GetAllAuthorsUseCase {
    private final AuthorRepository authorRepository;

    public GetAllAuthorsUseCase(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> execute() {
        return authorRepository.findAll();
    }
}
