package com.revolut.moneytransferapi.repository.implementation;

import com.revolut.moneytransferapi.util.EntityManagerUtil;
import com.revolut.moneytransferapi.domain.PersonalBankingAccount;
import com.revolut.moneytransferapi.repository.PersonalBankingAccountRepository;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonalBankingAccountRepositoryImpl implements PersonalBankingAccountRepository {

  private static final Logger logger = LoggerFactory.getLogger(PersonalBankingAccountRepositoryImpl.class);
  private EntityManager entityManager = EntityManagerUtil.getEntityManager();

  @Override
  public PersonalBankingAccount findByAccountNo(String accountNo) {
    TypedQuery<PersonalBankingAccount> query = entityManager.createQuery(
        "SELECT b FROM PersonalBankingAccount b WHERE b.accountNo = :accountNo", PersonalBankingAccount.class).setParameter("accountNo", accountNo);
    try{
      return query.getSingleResult();
    }catch(NoResultException n){
      logger.warn("not found account with this accountNo");
      return new PersonalBankingAccount();
    }
  }
}
