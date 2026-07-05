package com.frank.biblioteca.loan.application.service;

import java.time.LocalDate;
import java.util.UUID;

import com.frank.biblioteca.book.domain.model.BookModel;
import com.frank.biblioteca.book.domain.repository.BookRepository;
import com.frank.biblioteca.loan.domain.factory.LoanCalculatorFactory;
import com.frank.biblioteca.loan.domain.model.Loan;
import com.frank.biblioteca.loan.domain.model.value_object.LoanCost;
import com.frank.biblioteca.loan.domain.repository.LoanCalculator;
import com.frank.biblioteca.loan.domain.repository.LoanRepository;
import com.frank.biblioteca.user.domain.model.UserModel;
import com.frank.biblioteca.user.domain.repository.UserRepository;

public class ReturnBookUseCase {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final LoanCalculatorFactory loanCalculatorFactory;
    private final BookRepository bookRepository;

    public ReturnBookUseCase(LoanRepository loanRepository, UserRepository userRepository,
         LoanCalculatorFactory loanCalculatorFactory, BookRepository bookRepository){
        this.loanRepository=loanRepository;
        this.userRepository=userRepository;
        this.loanCalculatorFactory=loanCalculatorFactory;
        this.bookRepository=bookRepository;
    }

    public void returnBookExecute(LocalDate local, UUID loanId) {
        Loan loan = loanRepository.findById(loanId).
        orElseThrow(() -> new IllegalArgumentException("Loan not found with ID: " + loanId));

        UserModel user = userRepository.findById(loan.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + loan.getUserId()));
                
        LoanCalculator loanCalculator = loanCalculatorFactory.getCaculator(user.getRole());

        LoanCost loanCost = loanCalculator.calculate(loan);

        loan.returnBook(LocalDate.now(), loanCost);

        BookModel bookModel = bookRepository.findById(loan.getBookId()).orElseThrow(
            () -> new IllegalArgumentException("Book not found with ID " + loan.getBookId())
        );

        bookModel.setAvailable();

        loanRepository.save(loan);
        bookRepository.save(bookModel);

        System.out.println("Book returned successfully for Loan ID: " + loanId + ", Status: " + loan.getStatus() + ", Return Date: " + loan.getReturnDate());
    }
}
