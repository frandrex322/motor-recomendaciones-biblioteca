package com.frank.biblioteca.book.application.service;

import java.util.List;

import com.frank.biblioteca.book.domain.model.BookModel;
import com.frank.biblioteca.book.domain.repository.BookRepository;

public class FindBooksByAuthorUseCase {
    private final BookRepository bookRepository;

    public FindBooksByAuthorUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookModel> execute(String authorName) {
        return bookRepository.findByAuthorName(authorName);
    }
}
