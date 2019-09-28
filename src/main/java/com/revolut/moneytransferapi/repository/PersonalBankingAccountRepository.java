package com.revolut.moneytransferapi.repository;

import com.revolut.moneytransferapi.domain.PersonalBankingAccount;

public interface PersonalBankingAccountRepository {
  PersonalBankingAccount findByAccountNo(String accountNo);
}
