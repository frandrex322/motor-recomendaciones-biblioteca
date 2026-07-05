package com.frank.biblioteca.book.application.service;

import java.util.UUID;

import com.frank.biblioteca.book.domain.model.BookModel;
import com.frank.biblioteca.book.domain.repository.BookRepository;

public class GetBookByIdUseCase {
    private final BookRepository bookRepository;

    public GetBookByIdUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookModel execute(UUID id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + id));
    }
}
