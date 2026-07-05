package com.frank.biblioteca.book.models;

import java.time.LocalDateTime;
import java.util.UUID;

import com.frank.biblioteca.book.models.value_objects.BookInformation;
import com.frank.biblioteca.book.models.value_objects.BookStatus;

public class BookModel {
    private final UUID id;
    private final UUID authorId;
    private final String title;
    private final String description;
    private final BookInformation bookInformation;
    private BookStatus status;
    

    public BookModel(UUID id, UUID authorId, String title, String description, String isbn, int pages,
                     LocalDateTime publicationYear, BookStatus status, double price) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.description = description;
        this.bookInformation = new BookInformation(publicationYear.getYear(), isbn, price, pages);
        this.status = status;
        System.out.println("BookModel created with ID: " + this.id + ", Author ID: "+ "Price: $" + this.bookInformation.price() + ", Title: " + this.title + ", Description: " + this.description + ", ISBN: " + this.bookInformation.isbn() + ", Publication Year: " + this.bookInformation.publicationYear()  + ", Status: " + this.status);
    }


    public void MarkAsAvailable() {
        if(this.status == BookStatus.RESERVED || this.status == BookStatus.BORROWED) {
            throw new IllegalStateException("Cannot set status to AVAILABLE when the book is RESERVED or BORROWED");
        }
        this.status = BookStatus.VAILABLE;
    }

    public void Reserved() {
        if(this.status == BookStatus.BORROWED) {
            throw new IllegalStateException("Cannot set status to RESERVED when the book is BORROWED");
        }
        this.status = BookStatus.RESERVED;
    }

    public void markAsBorrowed() {
        if(this.status == BookStatus.RESERVED) {
            throw new IllegalStateException("Cannot set status to BORROWED when the book is RESERVED");
        }
        this.status = BookStatus.BORROWED;
    }

    public void setLost() {
        if(this.status == BookStatus.BORROWED) {
            throw new IllegalStateException("Cannot set status to LOST when the book is BORROWED");
        }
        this.status = BookStatus.LOST;
    }

    public void setDamaged() {
        if(this.status == BookStatus.BORROWED) {
            throw new IllegalStateException("Cannot set status to DAMAGED when the book is BORROWED");
        }
        this.status = BookStatus.DAMAGED;
    }

    public void setInactive() {
        if(this.status == BookStatus.BORROWED) {
            throw new IllegalStateException("Cannot set status to INACTIVE when the book is BORROWED");
        }
        this.status = BookStatus.INACTIVE;
    }

    public String getName() {
        return title;
    }

    @Override
    public String toString() {
        return "BookModel{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isbn='" + bookInformation.isbn() + '\'' +
                ", publicationYear=" + bookInformation.publicationYear() +
                ", status=" + status +
                '}';
    }

    

}
