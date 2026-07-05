package com.frank.biblioteca.book.infrastructure.mapper;

import java.time.LocalDateTime;

import com.frank.biblioteca.book.domain.factory.BookFactory;
import com.frank.biblioteca.book.domain.model.BookModel;
import com.frank.biblioteca.book.domain.model.value_objects.BookStatus;
import com.frank.biblioteca.book.infrastructure.entity.BookEntity;

public class BookMapper {

    public static BookEntity toEntity(BookModel domain) {
        return new BookEntity(domain.getId(), domain.getAuthorId(), domain.getTitle(),
            domain.getDescription(), domain.getBookInformation().isbn(),
            domain.getBookInformation().publicationYear(), domain.getBookInformation().price(),
            domain.getBookInformation().pages(), domain.getStatus().name(), domain.getImage());
    }

    public static BookModel toDomain(BookEntity entity) {
        BookModel model = BookFactory.getInstance(entity.getAuthorId(), entity.getTitle(),
            entity.getDescription(), entity.getIsbn(),
            LocalDateTime.of(entity.getPublicationYear(), 1, 1, 0, 0),
            BookStatus.valueOf(entity.getStatus()), entity.getPrice(), entity.getPages(), entity.getImage());
        return model;
    }
}
