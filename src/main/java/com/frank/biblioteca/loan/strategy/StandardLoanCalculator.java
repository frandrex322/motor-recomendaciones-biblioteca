package com.frank.biblioteca.loan.strategy;

import java.math.BigDecimal;

import com.frank.biblioteca.loan.domain.model.Loan;
import com.frank.biblioteca.loan.domain.model.value_object.LoanCost;
import com.frank.biblioteca.loan.domain.repository.LoanCalculator;
   
public class StandardLoanCalculator implements LoanCalculator {
    private static final BigDecimal DAILY_FEE = new BigDecimal("1.00");
    private static final BigDecimal OVERDUE_FEE_PER_DAY = new BigDecimal("0.50");

    @Override
    public LoanCost calculate(Loan loan) {
        BigDecimal loanFee = DAILY_FEE.multiply(BigDecimal.valueOf(loan.getLoanDurationInDays()));
        BigDecimal overdueFee = OVERDUE_FEE_PER_DAY.multiply(BigDecimal.valueOf(loan.getOverdueDays(loan.getReturnDate())));

        return new LoanCost(loanFee, overdueFee);
    }

    }
