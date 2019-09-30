package com.revolut.moneytransferapi.service;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.revolut.moneytransferapi.domain.BusinessBankingAccount;
import com.revolut.moneytransferapi.domain.Company;
import com.revolut.moneytransferapi.dto.MoneyTransferRequestDTO;
import com.revolut.moneytransferapi.dto.MoneyTransferResponseDTO;
import com.revolut.moneytransferapi.repository.BusinessBankingAccountRepository;
import java.math.BigDecimal;
import java.util.Currency;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MoneyTransferServiceTest {

  @InjectMocks
  private MoneyTransferServiceImpl moneyTransferService;

  @Mock
  private BusinessBankingAccountRepository businessBankingAccountRepository;

  @Mock
  private EntityManager entityManager;

  @Mock
  private EntityTransaction transaction;

  private final String ACCOUNT_NO="12345678";

  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldNotContinueProcessingMoneyTransferWhenSenderAccountDoesntExist(){
    when(businessBankingAccountRepository.findByAccountNo(Mockito.anyString())).thenReturn(new BusinessBankingAccount());
    MoneyTransferResponseDTO moneyTransferResponseDTO =
        moneyTransferService.moneyTransfer(new MoneyTransferRequestDTO("","", BigDecimal.valueOf(100),true,false));

    Assert.assertFalse(moneyTransferResponseDTO.isTransferResult());
    Assert.assertEquals("Not found Sender Account", moneyTransferResponseDTO.getMessage());

  }

  @Test
  public void shouldNotContinueProcessingMoneyTransferWhenReceiverAccountDoesntExist(){
    when(businessBankingAccountRepository.findByAccountNo(Mockito.anyString())).thenReturn(new BusinessBankingAccount(new Company(),ACCOUNT_NO,"",
        Currency.getInstance("EUR"),BigDecimal.valueOf(1000),true));

    MoneyTransferResponseDTO moneyTransferResponseDTO =
        moneyTransferService.moneyTransfer(new MoneyTransferRequestDTO(ACCOUNT_NO,"", BigDecimal.valueOf(100),true,false));

    Assert.assertFalse(moneyTransferResponseDTO.isTransferResult());
    Assert.assertEquals("Not found Receiver Account", moneyTransferResponseDTO.getMessage());
  }

  @Test
  public void shouldNotContinueProcessingMoneyTransferWhenSenderAccountBalanceLowerThanTransferAmount(){
    when(businessBankingAccountRepository.findByAccountNo(Mockito.anyString())).thenReturn(new BusinessBankingAccount(new Company(),ACCOUNT_NO,"",
        Currency.getInstance("EUR"),BigDecimal.valueOf(100),true));

    MoneyTransferResponseDTO moneyTransferResponseDTO =
        moneyTransferService.moneyTransfer(new MoneyTransferRequestDTO(ACCOUNT_NO,"", BigDecimal.valueOf(1000),true,true));

    Assert.assertFalse(moneyTransferResponseDTO.isTransferResult());
    Assert.assertEquals("Not enough balance to send money", moneyTransferResponseDTO.getMessage());
  }

  @Test
  public void shouldProcessingMoneyTransfer(){
    BusinessBankingAccount senderAccount = new BusinessBankingAccount(new Company(),ACCOUNT_NO,"",
        Currency.getInstance("EUR"),BigDecimal.valueOf(1000),true);
    BusinessBankingAccount receiverAccount = new BusinessBankingAccount(new Company(),"11111111","",
        Currency.getInstance("EUR"),BigDecimal.valueOf(1000),true);
    when(businessBankingAccountRepository.findByAccountNo(ACCOUNT_NO)).thenReturn(senderAccount);
    when(businessBankingAccountRepository.findByAccountNo("11111111")).thenReturn(receiverAccount);
    when(entityManager.getTransaction()).thenReturn(transaction);

    moneyTransferService.moneyTransfer(new MoneyTransferRequestDTO(ACCOUNT_NO,"11111111", BigDecimal.valueOf(100),
        true,true));

    //These verifications can prove updating sender and receiver accounts
    verify(entityManager, atLeastOnce()).merge(senderAccount);
    verify(entityManager, atLeastOnce()).merge(receiverAccount);

  }
}
