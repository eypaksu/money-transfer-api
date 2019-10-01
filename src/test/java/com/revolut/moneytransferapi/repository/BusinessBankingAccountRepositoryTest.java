package com.revolut.moneytransferapi.repository;

import com.revolut.moneytransferapi.EntityManagerUtil;
import com.revolut.moneytransferapi.domain.Address;
import com.revolut.moneytransferapi.domain.BusinessBankingAccount;
import com.revolut.moneytransferapi.domain.Company;
import com.revolut.moneytransferapi.repository.implementation.BusinessBankingAccountRepositoryImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BusinessBankingAccountRepositoryTest {

  private EntityManager entityManager = EntityManagerUtil.getEntityManager();
  private BusinessBankingAccountRepository businessBankingAccountRepository;

  private final String ACCOUNT_NO="12345678";

  @Before
  public void setUp(){
    businessBankingAccountRepository = new BusinessBankingAccountRepositoryImpl();
    entityManager.getTransaction().begin();
    Address address = new Address("33 Chamberlayne Road", "NW103NB");
    entityManager.persist(address);
    Company company = new Company("Apple", LocalDate.of(1990, 12, 12), address);
    entityManager.persist(company);
    entityManager.persist(new BusinessBankingAccount(company,ACCOUNT_NO, "Apple Banking Account", Currency
        .getInstance("USD"), BigDecimal.valueOf(100000000), true));
  }

  @After
  public void tearDown(){
    entityManager.getTransaction().rollback();
  }

  @Test
  public void shouldReturnAccountFromDatabase(){
    BusinessBankingAccount businessBankingAccount = businessBankingAccountRepository
        .findByAccountNo(ACCOUNT_NO);
    Assert.assertEquals(ACCOUNT_NO, businessBankingAccount.getAccountNo());
    Assert.assertEquals("Apple Banking Account", businessBankingAccount.getAccountName());
    Assert.assertEquals("Apple", businessBankingAccount.getCompany().getName());
  }

  @Test
  public void shouldReturnNullIfAccountDoesntExist(){
    BusinessBankingAccount businessBankingAccount1 = businessBankingAccountRepository.findByAccountNo("Something");
    Assert.assertNull(businessBankingAccount1.getAccountNo());
    Assert.assertNull(businessBankingAccount1.getAccountName());
    Assert.assertNull(businessBankingAccount1.getCompany());
  }



}
