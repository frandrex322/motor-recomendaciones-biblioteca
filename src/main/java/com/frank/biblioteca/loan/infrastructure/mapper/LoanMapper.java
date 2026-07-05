package com.frank.biblioteca.loan.infrastructure.mapper;

import java.math.BigDecimal;

import com.frank.biblioteca.loan.domain.model.Loan;
import com.frank.biblioteca.loan.domain.model.value_object.LoanCost;
import com.frank.biblioteca.loan.domain.model.value_object.LoanStatus;
import com.frank.biblioteca.loan.infrastructure.entity.LoanEntity;

public class LoanMapper {

    public static LoanEntity toEntity(Loan domain) {
        LoanEntity e = new LoanEntity();
        e.setId(domain.getId());
        e.setUserId(domain.getUserId());
        e.setBookId(domain.getBookId());
        e.setLoanDate(domain.getLoanPeriod().getLoanDate());
        e.setDueDate(domain.getLoanPeriod().getDueDate());
        e.setReturnDate(domain.getReturnDate());
        e.setStatus(domain.getStatus().name());
        return e;
    }

    public static Loan toDomain(LoanEntity entity) {
        Loan loan = new Loan(entity.getId(), entity.getUserId(), entity.getBookId(),
            entity.getLoanDate(), entity.getDueDate(), LoanStatus.valueOf(entity.getStatus()));
        if (entity.getReturnDate() != null) {
            LoanCost cost = new LoanCost(
                entity.getLoanFee() != null ? entity.getLoanFee() : BigDecimal.ZERO,
                entity.getOverdueFee() != null ? entity.getOverdueFee() : BigDecimal.ZERO);
            loan.returnBook(entity.getReturnDate(), cost);
        }
        return loan;
    }
}
