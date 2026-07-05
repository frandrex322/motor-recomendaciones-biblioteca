package com.frank.biblioteca.loan.application.service;

import java.util.List;

import com.frank.biblioteca.loan.domain.model.Loan;
import com.frank.biblioteca.loan.domain.repository.LoanRepository;

public class GetAllLoansUseCase {
    private final LoanRepository loanRepository;

    public GetAllLoansUseCase(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> execute() {
        return loanRepository.findAll();
    }
}
