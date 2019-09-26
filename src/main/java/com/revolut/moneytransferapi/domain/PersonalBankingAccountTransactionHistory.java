package com.revolut.moneytransferapi.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PersonalBankingAccountTransactionHistory extends BankingAccountTransactionHistory{

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "accountNo",
      referencedColumnName = "accountNo"
  )
  private PersonalBankingAccount personalBankingAccount;
}
