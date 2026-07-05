package com.frank.biblioteca.loan.application.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.frank.biblioteca.loan.domain.model.Loan;
import com.frank.biblioteca.loan.domain.model.value_object.LoanStatus;
import com.frank.biblioteca.loan.domain.repository.LoanRepository;

public class GetActiveLoansByUserUseCase {
    private final LoanRepository loanRepository;

    public GetActiveLoansByUserUseCase(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> execute(UUID userId) {
        return loanRepository.findByUserId(userId).stream()
            .filter(loan -> loan.getStatus() == LoanStatus.ACTIVE || loan.getStatus() == LoanStatus.OVERDUE)
            .collect(Collectors.toList());
    }
}
