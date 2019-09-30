package com.revolut.moneytransferapi.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyTransferRequestDTO {

  private String senderAccountNo;
  private String receiverAccountNo;
  private BigDecimal transactionAmount;
  private boolean isSenderBusinessAccount;
  private boolean isReceiverBusinessAccount;


}
