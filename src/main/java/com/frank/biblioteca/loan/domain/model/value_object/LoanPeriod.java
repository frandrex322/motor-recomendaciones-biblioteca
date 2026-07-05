package com.frank.biblioteca.loan.domain.model.value_object;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoanPeriod {
    private final LocalDate loanDate;
    private final LocalDate dueDate;

    public LoanPeriod(LocalDate loanDate, LocalDate dueDate) {
        if (loanDate.isAfter(dueDate)) {
            throw new IllegalArgumentException("Loan date must be before due date");
        }
        if (dueDate.isBefore(loanDate)) {
            throw new IllegalArgumentException("Due date must be after loan date");
        }
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public long durationUntil() {
        return ChronoUnit.DAYS.between(loanDate, dueDate);
    }

    public long overdueDays(LocalDate returnDate) {
        if (!returnDate.isAfter(dueDate)) {
            return 0;
        }
        return ChronoUnit.DAYS.between(dueDate, returnDate);
    }

    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getDueDate() { return dueDate; }

    @Override
    public String toString() {
        return "LoanPeriod{" +
                "loanDate=" + loanDate +
                ", dueDate=" + dueDate +
                '}';
    }
}
