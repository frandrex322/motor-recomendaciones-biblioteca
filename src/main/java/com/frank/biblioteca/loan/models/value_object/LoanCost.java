package com.frank.biblioteca.loan.models.value_object;

import java.math.BigDecimal;

public class LoanCost{
     private BigDecimal loanFee;
     private BigDecimal overdueFee;
     private BigDecimal totalCost;


     public LoanCost(BigDecimal loanFee, BigDecimal overdueFee) {
          this.loanFee = loanFee;
          this.overdueFee = overdueFee;
     }

     public BigDecimal getLoanFee() {
          return loanFee;
     }

     public BigDecimal getOverdueFee() {
          return overdueFee;
     }

     public BigDecimal getTotalCost() {
          return totalCost;
     }

    @Override
    public String toString() {
        return "LoanCost{" +
                "loanFee=" + loanFee +
                ", overdueFee=" + overdueFee +
                ", totalCost=" + totalCost +
                '}';
    }

     

}
