package com.revolut.moneytransferapi.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String name;

  @Column
  private LocalDate dateOfFound;

  @OneToOne
  private Address address;

  public Company(String name, LocalDate dateOfFound,
      Address address) {
    this.name = name;
    this.dateOfFound = dateOfFound;
    this.address = address;
  }
}
