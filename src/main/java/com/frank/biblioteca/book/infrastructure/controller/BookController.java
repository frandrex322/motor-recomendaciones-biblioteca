package com.frank.biblioteca.book.infrastructure.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.frank.biblioteca.book.domain.factory.BookFactory;
import com.frank.biblioteca.book.domain.model.BookModel;
import com.frank.biblioteca.book.domain.model.value_objects.BookStatus;
import com.frank.biblioteca.book.domain.repository.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookModel create(@RequestBody CreateBookRequest request) {
        BookModel book = BookFactory.getInstance(request.authorId, request.title, request.description,
            request.isbn, request.publicationYear, BookStatus.valueOf(request.status), request.price, request.pages, request.image);
        bookRepository.save(book);
        return book;
    }

    @GetMapping
    public List<BookModel> getAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public BookModel getById(@PathVariable UUID id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @GetMapping("/status/{status}")
    public List<BookModel> findByStatus(@PathVariable String status) {
        return bookRepository.findByStatus(status);
    }

    @PatchMapping("/{id}/status")
    public BookModel updateStatus(@PathVariable UUID id, @RequestBody UpdateStatusRequest request) {
        BookModel book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        switch (request.status.toUpperCase()) {
            case "VAILABLE" -> book.setAvailable();
            case "RESERVED" -> book.setReserved();
            case "BORROWED" -> book.setBorrowed();
            case "LOST" -> book.setLost();
            case "DAMAGED" -> book.setDamaged();
            case "INACTIVE" -> book.setInactive();
        }
        bookRepository.save(book);
        return book;
    }

    @PatchMapping("/{id}/image")
    public BookModel updateImage(@PathVariable UUID id, @RequestBody UpdateImageRequest request) {
        BookModel book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setImage(request.image);
        bookRepository.save(book);
        return book;
    }

    record CreateBookRequest(UUID authorId, String title, String description, String isbn,
        LocalDateTime publicationYear, String status, double price, int pages, String image) {}

    record UpdateStatusRequest(String status) {}

    record UpdateImageRequest(String image) {}
}
