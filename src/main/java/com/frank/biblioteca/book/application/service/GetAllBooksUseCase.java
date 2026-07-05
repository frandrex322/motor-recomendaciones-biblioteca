package com.frank.biblioteca.book.application.service;

import java.util.List;

import com.frank.biblioteca.book.domain.model.BookModel;
import com.frank.biblioteca.book.domain.repository.BookRepository;

public class GetAllBooksUseCase {
    private final BookRepository bookRepository;

    public GetAllBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookModel> execute() {
        return bookRepository.findAll();
    }
}
