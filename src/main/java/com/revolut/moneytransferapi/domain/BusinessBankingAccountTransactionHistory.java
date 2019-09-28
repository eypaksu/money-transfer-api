package com.revolut.moneytransferapi.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class BusinessBankingAccountTransactionHistory extends BankingAccountTransactionHistory{

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "accountNo",
      referencedColumnName = "accountNo"
  )
  private BusinessBankingAccount businessBankingAccount;

  public BusinessBankingAccountTransactionHistory(
      BusinessBankingAccount businessBankingAccount,
      String transactionType,
      LocalDateTime transactionTime,
      BigDecimal transactionAmount) {
    this.setTransactionTime(transactionTime);
    this.setTransactionAmount(transactionAmount);
    this.setTransactionType(transactionType);
    this.businessBankingAccount = businessBankingAccount;
  }
}
