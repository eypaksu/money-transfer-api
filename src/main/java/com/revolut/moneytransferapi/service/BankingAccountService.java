package com.revolut.moneytransferapi.service;

import com.revolut.moneytransferapi.dto.BankingTransactionHistoryResponseDTO;

public interface BankingAccountService {

  BankingTransactionHistoryResponseDTO getBankStatementForLastThirtyDays(boolean isBusiness, String accountNo);

}
