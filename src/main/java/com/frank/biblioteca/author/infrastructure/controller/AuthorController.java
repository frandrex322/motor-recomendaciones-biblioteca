package com.frank.biblioteca.author.infrastructure.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.frank.biblioteca.author.domain.factory.AuthorFactory;
import com.frank.biblioteca.author.domain.model.Author;
import com.frank.biblioteca.author.domain.repository.AuthorRepository;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author create(@RequestBody CreateAuthorRequest request) {
        Author author = AuthorFactory.getInstance(request.fullName, request.biography, request.birthDate, request.nationality);
        authorRepository.save(author);
        return author;
    }

    @GetMapping
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Author getById(@PathVariable UUID id) {
        return authorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @GetMapping("/name/{name}")
    public Author findByName(@PathVariable String name) {
        Author author = authorRepository.findByFullName(name);
        if (author == null) throw new RuntimeException("Author not found");
        return author;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        authorRepository.deleteById(id);
    }

    record CreateAuthorRequest(String fullName, String biography, LocalDate birthDate, String nationality) {}
}
