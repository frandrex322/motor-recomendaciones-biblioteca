package com.frank.biblioteca.loan.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import com.frank.biblioteca.loan.domain.model.value_object.LoanCost;
import com.frank.biblioteca.loan.domain.model.value_object.LoanPeriod;
import com.frank.biblioteca.loan.domain.model.value_object.LoanStatus;

public class Loan {
    private final UUID id;
    private final UUID userId;
    private final UUID bookId;
    private final LoanPeriod loanPeriod;
    private LocalDate returnDate;
    private LoanStatus status;
    private LoanCost loanCost;

    public Loan(UUID id, UUID userId, UUID bookId, LocalDate loanDate, LocalDate dueDate, LoanStatus status) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.loanPeriod = new LoanPeriod(loanDate, dueDate);
        this.status = status;
        System.out.println("Loan created with ID: " + this.id + ", User ID: " + this.userId + ", Book ID: " + this.bookId + ", Loan Period: " + this.loanPeriod + ", Return Date: " + this.returnDate + ", Status: " + this.status);
    }

    public UUID getId() { return id; }

    public void setActive() {
        if(this.status == LoanStatus.RETURNED || this.status == LoanStatus.CANCELLED) {
            throw new IllegalStateException("Cannot set status to ACTIVE when the loan is RETURNED or CANCELLED");
        }
        this.status = LoanStatus.ACTIVE;
    }

    public void returnBook(LocalDate returnDate, LoanCost loanCost) {
        if(this.status == LoanStatus.CANCELLED) {
            throw new IllegalStateException("Cannot set status to RETURNED when the loan is CANCELLED");
        }
        this.returnDate = returnDate;
        this.loanCost = loanCost;
        this.status = LoanStatus.RETURNED;
    }

    public void setOverdue() {
        if(this.status == LoanStatus.RETURNED || this.status == LoanStatus.CANCELLED) {
            throw new IllegalStateException("Cannot set status to OVERDUE when the loan is RETURNED or CANCELLED");
        }
        this.status = LoanStatus.OVERDUE;
    }

    public void setCancelled() {
        if(this.status == LoanStatus.RETURNED) {
            throw new IllegalStateException("Cannot set status to CANCELLED when the loan is RETURNED");
        }
        this.status = LoanStatus.CANCELLED;
    }

    public UUID getBookId() { return bookId; }
    public LoanPeriod getLoanPeriod() { return loanPeriod; }
    public LoanStatus getStatus() { return status; }
    public UUID getUserId() { return userId; }
    public LocalDate getReturnDate() { return returnDate; }

    public int getOverdueDays(LocalDate returnDate) {
        return (int) loanPeriod.overdueDays(returnDate);
    }

    public int getLoanDurationInDays() {
        return (int) loanPeriod.durationUntil();
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", loanPeriod=" + loanPeriod +
                ", returnDate=" + returnDate +
                ", status=" + status +
                '}';
    }
}
