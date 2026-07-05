package com.frank.biblioteca.loan.infrastructure.controller;

import java.time.LocalDate;
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

import com.frank.biblioteca.book.domain.repository.BookRepository;
import com.frank.biblioteca.loan.domain.factory.LoanCalculatorFactory;
import com.frank.biblioteca.loan.domain.factory.LoanFactory;
import com.frank.biblioteca.loan.domain.model.Loan;
import com.frank.biblioteca.loan.domain.repository.LoanRepository;
import com.frank.biblioteca.user.domain.repository.UserRepository;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanController(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Loan create(@RequestBody CreateLoanRequest request) {
        var book = bookRepository.findById(request.bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setBorrowed();
        bookRepository.save(book);
        Loan loan = LoanFactory.getInstance(request.userId, request.bookId, request.loanDate, request.dueDate);
        loanRepository.save(loan);
        return loan;
    }

    @GetMapping
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }

    @GetMapping("/{id}")
    public Loan getById(@PathVariable UUID id) {
        return loanRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    @GetMapping("/user/{userId}")
    public List<Loan> findByUser(@PathVariable UUID userId) {
        return loanRepository.findByUserId(userId);
    }

    @PatchMapping("/{id}/return")
    public Loan returnBook(@PathVariable UUID id) {
        Loan loan = loanRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Loan not found"));
        var user = userRepository.findById(loan.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        var calculator = LoanCalculatorFactory.getCaculator(user.getRole());
        var cost = calculator.calculate(loan);
        loan.returnBook(LocalDate.now(), cost);
        var book = bookRepository.findById(loan.getBookId())
            .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setAvailable();
        loanRepository.save(loan);
        bookRepository.save(book);
        return loan;
    }

    @PatchMapping("/{id}/cancel")
    public Loan cancel(@PathVariable UUID id) {
        Loan loan = loanRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Loan not found"));
        loan.setCancelled();
        var book = bookRepository.findById(loan.getBookId())
            .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setAvailable();
        loanRepository.save(loan);
        bookRepository.save(book);
        return loan;
    }

    record CreateLoanRequest(UUID userId, UUID bookId, LocalDate loanDate, LocalDate dueDate) {}
}
