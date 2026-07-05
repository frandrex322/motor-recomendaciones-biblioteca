package com.frank.biblioteca.book.models.value_objects;

public record BookInformation(
    int publicationYear,
    String isbn,
    double price,
    int pages
) {
    
}
