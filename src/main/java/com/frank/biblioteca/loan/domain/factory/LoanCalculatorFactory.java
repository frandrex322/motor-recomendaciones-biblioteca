package com.frank.biblioteca.loan.domain.factory;

import com.frank.biblioteca.loan.domain.repository.LoanCalculator;
import com.frank.biblioteca.loan.strategy.LibrarianLoanCalculator;
import com.frank.biblioteca.loan.strategy.PremiunLoanCalculator;
import com.frank.biblioteca.loan.strategy.StandardLoanCalculator;
import com.frank.biblioteca.loan.strategy.StudentLoanCalculator;
import com.frank.biblioteca.user.domain.model.value_object.UserRole;

public class LoanCalculatorFactory {

    public static LoanCalculator getCaculator(UserRole role) {
        switch (role) {
            case UserRole.STUDENT:
                return new StudentLoanCalculator();
            case UserRole.TEACHER:
                return new PremiunLoanCalculator();
            case UserRole.LIBRARIAN:
                return new LibrarianLoanCalculator();
            case UserRole.STANDARD_USER:
                return new StandardLoanCalculator();
            default:
                throw new IllegalArgumentException("Invalid user role: " + role);
        }
    }
}
