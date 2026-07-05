package com.frank.biblioteca.loan.domain.factory;

import java.time.LocalDate;
import java.util.UUID;

import com.frank.biblioteca.loan.domain.model.Loan;
import com.frank.biblioteca.loan.domain.model.value_object.LoanStatus;

public class LoanFactory {

    public static Loan getInstance(UUID userId, UUID bookId, LocalDate loanDate, LocalDate dueDate) {
        return new Loan(UUID.randomUUID(), userId, bookId, loanDate, dueDate, LoanStatus.ACTIVE);
    }
}
