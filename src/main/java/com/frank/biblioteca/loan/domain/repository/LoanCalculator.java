package com.frank.biblioteca.loan.domain.repository;

import com.frank.biblioteca.loan.domain.model.Loan;
import com.frank.biblioteca.loan.domain.model.value_object.LoanCost;

public interface LoanCalculator {
    LoanCost calculate(Loan loan);
}
