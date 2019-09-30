package com.revolut.moneytransferapi.repository;

import com.revolut.moneytransferapi.domain.PersonalBankingAccount;
import com.revolut.moneytransferapi.domain.PersonalBankingAccountTransactionHistory;
import java.util.List;


public interface PersonalBankingAccountTransactionHistoryRepository {
  List<PersonalBankingAccountTransactionHistory> getTransactionHistoryList(
      PersonalBankingAccount personalBankingAccount);
}
