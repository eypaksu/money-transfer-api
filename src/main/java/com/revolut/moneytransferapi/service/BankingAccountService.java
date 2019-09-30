package com.revolut.moneytransferapi.service;

import com.revolut.moneytransferapi.dto.BankingAccountTransactionHistoryResponseDTO;

public interface BankingAccountService {

  BankingAccountTransactionHistoryResponseDTO getBankStatement(boolean isBusiness, String accountNo);

}
