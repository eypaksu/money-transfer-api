package com.revolut.moneytransferapi.repository.implementation;

import com.revolut.moneytransferapi.domain.PersonalBankingAccountTransactionHistory;
import com.revolut.moneytransferapi.repository.PersonalBankingAccountTransactionHistoryRepository;
import java.util.List;

public class PersonalBankingAccountTransactionHistoryRepositoryImpl implements
    PersonalBankingAccountTransactionHistoryRepository {

  @Override
  public List<PersonalBankingAccountTransactionHistory> getTransactionHistoryList(
      String accountNo) {
    return null;
  }
}
