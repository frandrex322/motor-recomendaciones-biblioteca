package com.frank.biblioteca.loan.application.service;

import java.util.UUID;

import com.frank.biblioteca.book.domain.repository.BookRepository;
import com.frank.biblioteca.loan.domain.model.Loan;
import com.frank.biblioteca.loan.domain.repository.LoanRepository;

public class CancelLoanUseCase {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    public CancelLoanUseCase(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    public void execute(UUID loanId) {
        Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new IllegalArgumentException("Loan not found with ID: " + loanId));
        loan.setCancelled();
        var book = bookRepository.findById(loan.getBookId())
            .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + loan.getBookId()));
        book.setAvailable();
        loanRepository.save(loan);
        bookRepository.save(book);
    }
}
