package com.revolut.moneytransferapi.repository;

import com.revolut.moneytransferapi.EntityManagerUtil;
import com.revolut.moneytransferapi.domain.Address;
import com.revolut.moneytransferapi.domain.BusinessBankingAccount;
import com.revolut.moneytransferapi.domain.BusinessBankingAccountTransactionHistory;
import com.revolut.moneytransferapi.domain.Company;
import com.revolut.moneytransferapi.domain.TransactionType;
import com.revolut.moneytransferapi.repository.implementation.BusinessBankingAccountTransactionHistoryRepositoryImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BusinessBankingAccountTransactionHistoryTest {

  private EntityManager entityManager = EntityManagerUtil.getEntityManager();
  private BusinessBankingAccountTransactionHistoryRepository businessBankingAccountTransactionHistoryRepository;
  private BusinessBankingAccount businessBankingAccount;

  @Before
  public void setUp(){
    businessBankingAccountTransactionHistoryRepository = new BusinessBankingAccountTransactionHistoryRepositoryImpl();
    entityManager.getTransaction().begin();
    Address address = new Address("33 Chamberlayne Road", "NW103NB");
    entityManager.persist(address);
    Company company = new Company("Apple", LocalDate.of(1990, 12, 12), address);
    entityManager.persist(company);
    String ACCOUNT_NO = "12345678";
    businessBankingAccount = new BusinessBankingAccount(company, ACCOUNT_NO, "Apple Banking Account", Currency
        .getInstance("USD"), BigDecimal.valueOf(100000000), true);
    entityManager.persist(businessBankingAccount);
  }

  @After
  public void tearDown(){
    entityManager.getTransaction().rollback();
  }

  @Test
  public void shouldReturnTransactionHistory(){
    BusinessBankingAccountTransactionHistory transactionOne = new BusinessBankingAccountTransactionHistory(businessBankingAccount,
        TransactionType.OPEN_ACCOUNT.toString(), LocalDateTime.now().minusYears(2), BigDecimal.valueOf(10000));
    BusinessBankingAccountTransactionHistory transactionTwo = new BusinessBankingAccountTransactionHistory(businessBankingAccount,
        TransactionType.SENT_MONEY.toString(), LocalDateTime.now().minusYears(2), BigDecimal.valueOf(1000));

    entityManager.persist(transactionOne);
    entityManager.persist(transactionTwo);

    List<BusinessBankingAccountTransactionHistory> transactionHistoryList = businessBankingAccountTransactionHistoryRepository.getTransactionHistoryList(businessBankingAccount);

    Assert.assertEquals(2, transactionHistoryList.size());
    Assert.assertEquals(TransactionType.OPEN_ACCOUNT.toString(), transactionHistoryList.get(0).getTransactionType());

  }

  @Test
  public void shouldReturnTransactionHistoryOrderedByTransactionTime(){
    BusinessBankingAccountTransactionHistory transactionOne = new BusinessBankingAccountTransactionHistory(businessBankingAccount,
        TransactionType.OPEN_ACCOUNT.toString(), LocalDateTime.now().minusYears(3), BigDecimal.valueOf(10000));
    BusinessBankingAccountTransactionHistory transactionTwo = new BusinessBankingAccountTransactionHistory(businessBankingAccount,
        TransactionType.SENT_MONEY.toString(), LocalDateTime.now().minusYears(1), BigDecimal.valueOf(1000));
    BusinessBankingAccountTransactionHistory transactionThree = new BusinessBankingAccountTransactionHistory(businessBankingAccount,
        TransactionType.RECEIVED_MONEY.toString(), LocalDateTime.now().minusYears(2), BigDecimal.valueOf(1000));


    entityManager.persist(transactionOne);
    entityManager.persist(transactionTwo);
    entityManager.persist(transactionThree);

    List<BusinessBankingAccountTransactionHistory> transactionHistoryList = businessBankingAccountTransactionHistoryRepository.getTransactionHistoryList(businessBankingAccount);

    Assert.assertEquals(TransactionType.OPEN_ACCOUNT.toString(), transactionHistoryList.get(0).getTransactionType());
    Assert.assertEquals(TransactionType.RECEIVED_MONEY.toString(), transactionHistoryList.get(1).getTransactionType());
    Assert.assertEquals(TransactionType.SENT_MONEY.toString(), transactionHistoryList.get(2).getTransactionType());
  }

}
