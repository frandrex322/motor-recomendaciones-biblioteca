package com.frank.biblioteca.loan.domain.model.value_object;

import java.math.BigDecimal;

public class LoanCost{
     private BigDecimal loanFee;
     private BigDecimal overdueFee;
     private BigDecimal totalCost;

     public LoanCost(BigDecimal loanFee, BigDecimal overdueFee) {
          this.loanFee = loanFee;
          this.overdueFee = overdueFee;
     }

     public void calculateTotalCost(int cantidadDiasRetunr, int cantidadDiasDue,
          BigDecimal dailyOverdueFee
     ) {
          BigDecimal totalCost = loanFee.add(overdueFee);
          if (cantidadDiasRetunr > cantidadDiasDue) {
               int overdueDays = cantidadDiasRetunr - cantidadDiasDue;
               BigDecimal overdueCost = dailyOverdueFee.multiply(BigDecimal.valueOf(overdueDays));
               totalCost = totalCost.add(overdueCost);
          }
          this.totalCost = totalCost;
          System.out.println("Total Cost: " + totalCost);
     }

     public BigDecimal getLoanFee() { return loanFee; }
     public BigDecimal getOverdueFee() { return overdueFee; }
     public BigDecimal getTotalCost() { return totalCost; }

    @Override
    public String toString() {
        return "LoanCost{" +
                "loanFee=" + loanFee +
                ", overdueFee=" + overdueFee +
                ", totalCost=" + totalCost +
                '}';
    }
}
