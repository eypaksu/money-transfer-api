package com.revolut.moneytransferapi.domain;

import java.math.BigDecimal;
import java.util.Currency;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class BusinessBankingAccount extends BankingAccount{

  @ManyToOne
  private Company company;

  public BusinessBankingAccount(Company company, String accountNo, String accountName, Currency currency, BigDecimal balance, boolean isActive) {
    this.company = company;
    this.setAccountNo(accountNo);
    this.setAccountName(accountName);
    this.setBalance(balance);
    this.setCurrency(currency);
    this.setActive(isActive);
  }

}
