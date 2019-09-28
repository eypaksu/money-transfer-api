package com.revolut.moneytransferapi.dto;

import com.revolut.moneytransferapi.domain.BankingAccountTransactionHistory;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankingTransactionHistoryResponseDTO {

  private String accountName;
  private String accountNo;
  private List<BankingAccountTransactionHistory> bankingAccountTransactionHistoryList;

  public BankingTransactionHistoryResponseDTO(String accountNo,String accountName,
      List<BankingAccountTransactionHistory> bankingAccountTransactionHistoryList) {
    this.accountName = accountName;
    this.accountNo = accountNo;
    this.bankingAccountTransactionHistoryList = bankingAccountTransactionHistoryList;
  }
}
