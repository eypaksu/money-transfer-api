package com.revolut.moneytransferapi.repository;

import com.revolut.moneytransferapi.domain.BusinessBankingAccount;

public interface BusinessBankingAccountRepository {

  BusinessBankingAccount findByAccountNo(String accountNo);

}
