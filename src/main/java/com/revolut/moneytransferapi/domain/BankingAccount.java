package com.revolut.moneytransferapi.domain;

import java.math.BigDecimal;
import java.util.Currency;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
@MappedSuperclass
public abstract class BankingAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NaturalId
  @Column
  @NotNull
  @Pattern(regexp="[\\d]{8}") //validation
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
