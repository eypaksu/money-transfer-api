package com.revolut.moneytransferapi.dto;

import com.revolut.moneytransferapi.domain.BankingAccountTransactionHistory;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankingAccountTransactionHistoryResponseDTO {

  private String accountName;
  private String accountNo;
  private BigDecimal balance;
  private List<BankingAccountTransactionHistory> bankingAccountTransactionHistoryList;

  public BankingAccountTransactionHistoryResponseDTO(String accountNo,String accountName, BigDecimal balance,
      List<BankingAccountTransactionHistory> bankingAccountTransactionHistoryList) {
    this.accountName = accountName;
    this.accountNo = accountNo;
    this.balance = balance;
    this.bankingAccountTransactionHistoryList = bankingAccountTransactionHistoryList;
  }
}
