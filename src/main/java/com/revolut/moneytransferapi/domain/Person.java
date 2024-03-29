package com.revolut.moneytransferapi.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String name;

  @Column
  private String surname;

  @Column
  private LocalDate dateOfBirth;

  @OneToOne
  private Address address;

  public Person(String name, String surname, LocalDate dateOfBirth,
      Address address) {
    this.name = name;
    this.surname = surname;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
  }
}
