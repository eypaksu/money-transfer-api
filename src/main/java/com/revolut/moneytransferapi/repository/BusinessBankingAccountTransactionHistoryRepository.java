package com.revolut.moneytransferapi.repository;

import com.revolut.moneytransferapi.domain.BusinessBankingAccountTransactionHistory;
import java.util.List;

public interface BusinessBankingAccountTransactionHistoryRepository {
  List<BusinessBankingAccountTransactionHistory>getTransactionHistoryList(String accountNo);
}
