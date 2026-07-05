package com.frank.biblioteca.book.domain.model.value_objects;

public record BookInformation(
    int publicationYear,
    String isbn,
    double price,
    int pages
) {
    
}
