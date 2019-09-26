package com.revolut.moneytransferapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PersonalBankingAccount extends BankingAccount{

  @ManyToOne
  private Person person;

}
