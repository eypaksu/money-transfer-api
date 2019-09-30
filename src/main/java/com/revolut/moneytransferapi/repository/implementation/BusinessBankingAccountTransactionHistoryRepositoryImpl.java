package com.revolut.moneytransferapi.repository.implementation;

import com.revolut.moneytransferapi.EntityManagerUtil;
import com.revolut.moneytransferapi.domain.BusinessBankingAccount;
import com.revolut.moneytransferapi.domain.BusinessBankingAccountTransactionHistory;
import com.revolut.moneytransferapi.repository.BusinessBankingAccountTransactionHistoryRepository;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessBankingAccountTransactionHistoryRepositoryImpl implements
    BusinessBankingAccountTransactionHistoryRepository {

  private EntityManager entityManager = EntityManagerUtil.getEntityManager();
  private static final Logger logger = LoggerFactory.getLogger(BusinessBankingAccountTransactionHistoryRepositoryImpl.class);

  @Override
  public List<BusinessBankingAccountTransactionHistory> getTransactionHistoryList(
      BusinessBankingAccount businessBankingAccount) {

    TypedQuery<BusinessBankingAccountTransactionHistory> query = entityManager.createQuery
        ("SELECT bt FROM BusinessBankingAccountTransactionHistory bt WHERE bt.businessBankingAccount = :businessBankingAccount ORDER BY bt.transactionTime",
            BusinessBankingAccountTransactionHistory.class).setParameter("businessBankingAccount", businessBankingAccount);

    try{
      return query.getResultList();
    }catch(NoResultException n){
      logger.warn("not found account history with this account");
      return Collections.emptyList();
    }

  }


}
