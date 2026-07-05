package com.frank.biblioteca.author.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.frank.biblioteca.book.domain.model.BookModel;

public class Author {
    private final UUID id;
    private final String fullName;
    private final String biography;
    private final LocalDate birthDate;
    private final String nationality;
    private final List<BookModel> books = new ArrayList<>();

    public Author(final String fullName, final String biography,
         final LocalDate birthDate, final String nationality) {
        this.id = UUID.randomUUID();
        this.fullName = fullName;
        this.biography = biography;
        this.birthDate = birthDate;
        this.nationality = nationality; 
        System.out.println("Author created with ID: " + this.id + ", Full Name: " + this.fullName + ", Biography: " + this.biography + ", Birth Date: " + this.birthDate + ", Nationality: " + this.nationality);
        }

    public void addBook(final BookModel book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if(this.books.stream().filter(e -> e.getName().equals(book.getName())).findFirst().isPresent()) {
            throw new IllegalArgumentException("Book already exists in the author's list");
        }

        this.books.add(book);
    }

    public UUID getId() { return id; }

    public String getFullName() { return fullName; }

    public String getBiography() { return biography; }

    public LocalDate getBirthDate() { return birthDate; }

    public String getNationality() { return nationality; }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Author{");
        sb.append("id=").append(id);
        sb.append(", fullName=").append(fullName);
        sb.append(", biography=").append(biography);
        sb.append(", birthDate=").append(birthDate);
        sb.append(", nationality=").append(nationality);
        sb.append(", books=").append(books);
        sb.append('}');
        return sb.toString();
    }
}
