package com.revolut.moneytransferapi.dto;

import lombok.Data;

@Data
public class MoneyTransferResponseDTO {

  private boolean transferResult;

  private String message;

  public MoneyTransferResponseDTO(boolean transferResult, String message) {
    this.transferResult = transferResult;
    this.message = message;
  }
}
