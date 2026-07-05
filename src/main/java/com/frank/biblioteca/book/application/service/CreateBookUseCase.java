package com.frank.biblioteca.book.application.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.frank.biblioteca.book.domain.factory.BookFactory;
import com.frank.biblioteca.book.domain.model.value_objects.BookStatus;
import com.frank.biblioteca.book.domain.repository.BookRepository;

public class CreateBookUseCase {
    private final BookRepository bookRepository;

    public CreateBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void execute(UUID authorId, String title, String description, String isbn,
                        LocalDateTime publicationYear, BookStatus status, double price, int pages, String image) {
        bookRepository.save(BookFactory.getInstance(authorId, title, description, isbn, publicationYear, status, price, pages, image));
    }
}
