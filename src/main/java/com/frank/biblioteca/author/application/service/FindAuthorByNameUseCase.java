package com.frank.biblioteca.author.application.service;

import com.frank.biblioteca.author.domain.model.Author;
import com.frank.biblioteca.author.domain.repository.AuthorRepository;

public class FindAuthorByNameUseCase {
    private final AuthorRepository authorRepository;

    public FindAuthorByNameUseCase(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author execute(String fullName) {
        Author author = authorRepository.findByFullName(fullName);
        if (author == null) {
            throw new IllegalArgumentException("Author not found with name: " + fullName);
        }
        return author;
    }
}
