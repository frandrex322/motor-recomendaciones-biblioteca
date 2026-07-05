package com.frank.biblioteca.loan.domain.repository;

import java.util.List;
import java.util.UUID;

import com.frank.biblioteca.loan.domain.model.Loan;
import com.frank.biblioteca.utils.RepositoryGeneric;

public interface LoanRepository extends RepositoryGeneric<UUID, Loan> {
    List<Loan> findByUserId(UUID userId);
    List<Loan> findByBookId(UUID bookId);
    List<Loan> findByStatus(String status);
}
