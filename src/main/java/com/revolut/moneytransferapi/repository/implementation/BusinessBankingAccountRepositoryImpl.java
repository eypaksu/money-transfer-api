package com.revolut.moneytransferapi.repository.implementation;

import com.revolut.moneytransferapi.util.EntityManagerUtil;
import com.revolut.moneytransferapi.domain.BusinessBankingAccount;
import com.revolut.moneytransferapi.repository.BusinessBankingAccountRepository;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessBankingAccountRepositoryImpl implements BusinessBankingAccountRepository {

  private static final Logger logger = LoggerFactory.getLogger(BusinessBankingAccountRepositoryImpl.class);
  private EntityManager entityManager = EntityManagerUtil.getEntityManager();

  @Override
  public BusinessBankingAccount findByAccountNo(String accountNo) {
    TypedQuery<BusinessBankingAccount> query = entityManager.createQuery(
        "SELECT b FROM BusinessBankingAccount b WHERE b.accountNo = :accountNo", BusinessBankingAccount.class).setParameter("accountNo", accountNo);
    try{
      return query.getSingleResult();
    }catch(NoResultException n){
      logger.warn("not found account with this accountNo");
      return new BusinessBankingAccount();
    }
  }
}
