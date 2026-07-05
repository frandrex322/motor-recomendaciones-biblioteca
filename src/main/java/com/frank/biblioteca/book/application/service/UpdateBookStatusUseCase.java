package com.frank.biblioteca.book.application.service;

import java.util.UUID;

import com.frank.biblioteca.book.domain.model.BookModel;
import com.frank.biblioteca.book.domain.repository.BookRepository;

public class UpdateBookStatusUseCase {
    private final BookRepository bookRepository;

    public UpdateBookStatusUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void execute(UUID bookId, String newStatus) {
        BookModel book = bookRepository.findById(bookId)
            .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + bookId));

        switch (newStatus.toUpperCase()) {
            case "VAILABLE" -> book.setAvailable();
            case "RESERVED" -> book.setReserved();
            case "BORROWED" -> book.setBorrowed();
            case "LOST" -> book.setLost();
            case "DAMAGED" -> book.setDamaged();
            case "INACTIVE" -> book.setInactive();
            default -> throw new IllegalArgumentException("Invalid book status: " + newStatus);
        }

        bookRepository.save(book);
    }
}
