package com.revolut.moneytransferapi.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BusinessBankingAccount extends BankingAccount{

  @ManyToOne
  private Company company;
}
