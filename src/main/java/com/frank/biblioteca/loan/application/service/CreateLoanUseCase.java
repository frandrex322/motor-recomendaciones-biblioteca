package com.frank.biblioteca.loan.application.service;

import java.time.LocalDate;
import java.util.UUID;

import com.frank.biblioteca.book.domain.model.BookModel;
import com.frank.biblioteca.book.domain.repository.BookRepository;
import com.frank.biblioteca.loan.domain.factory.LoanFactory;
import com.frank.biblioteca.loan.domain.repository.LoanRepository;

public class CreateLoanUseCase {
    
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    public CreateLoanUseCase(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository= bookRepository;
    }

    public void createLoanExecute(UUID userId, UUID bookId, LocalDate loanDate, LocalDate dueDate){
        BookModel book = bookRepository.findById(bookId).orElseThrow(
            () -> new IllegalArgumentException("Book not found " + bookId)
        );
        book.setBorrowed();
        
        bookRepository.save(book);
        loanRepository.save(LoanFactory.getInstance(userId, bookId, loanDate, dueDate));
    }
}
