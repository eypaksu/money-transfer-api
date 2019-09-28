package com.revolut.moneytransferapi.repository.implementation;

import com.revolut.moneytransferapi.EntityManagerUtil;
import com.revolut.moneytransferapi.domain.BusinessBankingAccountTransactionHistory;
import com.revolut.moneytransferapi.repository.BusinessBankingAccountTransactionHistoryRepository;
import java.util.List;
import javax.persistence.EntityManager;

public class BusinessBankingAccountTransactionHistoryRepositoryImpl implements
    BusinessBankingAccountTransactionHistoryRepository {

  private EntityManager entityManager = EntityManagerUtil.getEntityManager();

  @Override
  public List<BusinessBankingAccountTransactionHistory> getTransactionHistoryList(
      String accountNo) {

    return entityManager.createQuery(
        "SELECT transactionAmount FROM BusinessBankingAccountTransactionHistory")
        .getResultList();

  }
}
