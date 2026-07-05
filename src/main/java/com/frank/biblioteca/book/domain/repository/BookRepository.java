package com.frank.biblioteca.book.domain.repository;

import java.util.List;
import java.util.UUID;

import com.frank.biblioteca.book.domain.model.BookModel;
import com.frank.biblioteca.utils.RepositoryGeneric;

public interface BookRepository extends RepositoryGeneric<UUID, BookModel> {
    List<BookModel> findByAuthorName(String authorName);
    List<BookModel> findByStatus(String status);
}
