package com.frank.biblioteca.loan.application.service;

import java.util.UUID;

import com.frank.biblioteca.loan.domain.model.Loan;
import com.frank.biblioteca.loan.domain.repository.LoanRepository;

public class GetLoanByIdUseCase {
    private final LoanRepository loanRepository;

    public GetLoanByIdUseCase(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan execute(UUID id) {
        return loanRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Loan not found with ID: " + id));
    }
}
