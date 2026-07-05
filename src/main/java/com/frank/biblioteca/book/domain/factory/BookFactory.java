package com.frank.biblioteca.book.domain.factory;

import java.time.LocalDateTime;
import java.util.UUID;

import com.frank.biblioteca.book.domain.model.BookModel;
import com.frank.biblioteca.book.domain.model.value_objects.BookStatus;

public class BookFactory {

    public static BookModel getInstance(UUID authorId, String title, String description, String isbn,
                     LocalDateTime publicationYear, BookStatus status, double price, int pages, String image) {
        return new BookModel(
            UUID.randomUUID(), authorId, title, description, isbn, pages, publicationYear, status, price, image);
    }
}
