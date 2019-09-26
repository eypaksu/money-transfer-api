package com.revolut.moneytransferapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Address {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String address;

  @Column
  private String postcode;

}
