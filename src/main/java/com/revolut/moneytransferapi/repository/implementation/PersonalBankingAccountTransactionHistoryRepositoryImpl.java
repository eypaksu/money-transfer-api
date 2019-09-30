package com.revolut.moneytransferapi.repository.implementation;

import com.revolut.moneytransferapi.EntityManagerUtil;
import com.revolut.moneytransferapi.domain.PersonalBankingAccount;
import com.revolut.moneytransferapi.domain.PersonalBankingAccountTransactionHistory;
import com.revolut.moneytransferapi.repository.PersonalBankingAccountTransactionHistoryRepository;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonalBankingAccountTransactionHistoryRepositoryImpl implements
    PersonalBankingAccountTransactionHistoryRepository {

  private EntityManager entityManager = EntityManagerUtil.getEntityManager();
  private static final Logger logger = LoggerFactory.getLogger(PersonalBankingAccountTransactionHistoryRepositoryImpl.class);

  @Override
  public List<PersonalBankingAccountTransactionHistory> getTransactionHistoryList(
      PersonalBankingAccount personalBankingAccount) {
    TypedQuery<PersonalBankingAccountTransactionHistory> query = entityManager.createQuery
        ("SELECT bt FROM PersonalBankingAccountTransactionHistory bt WHERE bt.personalBankingAccount = :personalBankingAccount ORDER BY bt.transactionTime",
            PersonalBankingAccountTransactionHistory.class).setParameter("personalBankingAccount", personalBankingAccount);
    try{
      return query.getResultList();
    }catch(NoResultException n){
      logger.warn("not found account history with this account");
      return Collections.emptyList();
    }

  }
}
