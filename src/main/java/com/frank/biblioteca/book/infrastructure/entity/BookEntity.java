package com.frank.biblioteca.book.infrastructure.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookEntity {
    @Id
    private UUID id;
    private UUID authorId;
    private String title;
    private String description;
    private String isbn;
    private int publicationYear;
    private double price;
    private int pages;
    private String status;
    private String image;
}
