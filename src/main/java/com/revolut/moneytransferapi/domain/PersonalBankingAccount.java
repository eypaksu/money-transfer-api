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
public class PersonalBankingAccount extends BankingAccount{

  @ManyToOne
  private Person person;

  public PersonalBankingAccount(Person person, String accountNo, String accountName, Currency currency, BigDecimal balance, boolean isActive) {
    this.person=person;
    this.setAccountName(accountName);
    this.setAccountNo(accountNo);
    this.setBalance(balance);
    this.setCurrency(currency);
    this.setActive(isActive);
  }
}
