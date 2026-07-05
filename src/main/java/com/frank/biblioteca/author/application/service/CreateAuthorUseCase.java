package com.frank.biblioteca.author.application.service;

import java.time.LocalDate;

import com.frank.biblioteca.author.domain.factory.AuthorFactory;
import com.frank.biblioteca.author.domain.repository.AuthorRepository;

public class CreateAuthorUseCase {
    private final AuthorRepository authorRepository;

    public CreateAuthorUseCase(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void execute(String fullName, String biography, LocalDate birthDate, String nationality) {
        authorRepository.save(AuthorFactory.getInstance(fullName, biography, birthDate, nationality));
    }
}
