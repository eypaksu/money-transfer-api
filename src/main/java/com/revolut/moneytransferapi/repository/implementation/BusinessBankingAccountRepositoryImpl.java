package com.revolut.moneytransferapi.repository.implementation;

import com.revolut.moneytransferapi.EntityManagerUtil;
import com.revolut.moneytransferapi.domain.BusinessBankingAccount;
import com.revolut.moneytransferapi.repository.BusinessBankingAccountRepository;
import javax.persistence.EntityManager;

public class BusinessBankingAccountRepositoryImpl implements BusinessBankingAccountRepository {

  EntityManager entityManager = EntityManagerUtil.getEntityManager();

  @Override
  public BusinessBankingAccount findByAccountNo(String accountNo) {
    Object o =  entityManager.createQuery(
        "SELECT accountName, accountNo, balance, currency  FROM BusinessBankingAccount WHERE accountNo='1234578'")
        .getSingleResult();

    return null;
  }
}
