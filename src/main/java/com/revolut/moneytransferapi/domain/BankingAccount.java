package com.revolut.moneytransferapi.domain;

import java.math.BigDecimal;
import java.util.Currency;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
@MappedSuperclass
public abstract class BankingAccount {

  @Id
  private String id;

  @NaturalId
  @Column
  private String accountNo;

  @Column
  private String accountName;

  @Column
  private BigDecimal balance;

  @Column
  private Currency currency;

  @Column
  private boolean isActive;

}
