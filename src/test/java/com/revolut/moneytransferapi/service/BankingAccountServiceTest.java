package com.revolut.moneytransferapi.service;

import static org.mockito.Mockito.when;

import com.revolut.moneytransferapi.domain.BusinessBankingAccount;
import com.revolut.moneytransferapi.domain.BusinessBankingAccountTransactionHistory;
import com.revolut.moneytransferapi.domain.Company;
import com.revolut.moneytransferapi.domain.Person;
import com.revolut.moneytransferapi.domain.PersonalBankingAccount;
import com.revolut.moneytransferapi.domain.PersonalBankingAccountTransactionHistory;
import com.revolut.moneytransferapi.domain.TransactionType;
import com.revolut.moneytransferapi.dto.BankingTransactionHistoryResponseDTO;
import com.revolut.moneytransferapi.repository.BusinessBankingAccountRepository;
import com.revolut.moneytransferapi.repository.BusinessBankingAccountTransactionHistoryRepository;
import com.revolut.moneytransferapi.repository.PersonalBankingAccountRepository;
import com.revolut.moneytransferapi.repository.PersonalBankingAccountTransactionHistoryRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class BankingAccountServiceTest {

  @InjectMocks
  private BankingAccountServiceImpl bankingAccountService;

  @Mock
  private BusinessBankingAccountRepository businessBankingAccountRepository;

  @Mock
  private PersonalBankingAccountRepository personalBankingAccountRepository;

  @Mock
  private BusinessBankingAccountTransactionHistoryRepository businessBankingAccountTransactionHistoryRepository;

  @Mock
  private PersonalBankingAccountTransactionHistoryRepository personalBankingAccountTransactionHistoryRepository;

  private final String ACCOUNT_NO="12345678";

  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldReturnEmptyListWhenAccountDoesntExist(){

    when(businessBankingAccountRepository.findByAccountNo(Mockito.anyString())).thenReturn(new BusinessBankingAccount());

    BankingTransactionHistoryResponseDTO bankingTransactionHistoryResponseDTO =
        bankingAccountService.getBankStatementForLastThirtyDays(true, ACCOUNT_NO);

    Assert.assertEquals(0, bankingTransactionHistoryResponseDTO.getBankingAccountTransactionHistoryList().size());
    Assert.assertNull(bankingTransactionHistoryResponseDTO.getAccountName());
    Assert.assertNull(bankingTransactionHistoryResponseDTO.getAccountNo());

  }

  @Test
  public void shouldReturnTransactionListAndAccountInfo(){
    BusinessBankingAccount businessBankingAccount = new BusinessBankingAccount(new Company(), ACCOUNT_NO, "Eyup Aksu", Currency
        .getInstance("USD"), BigDecimal.valueOf(1000000), true );
    when(businessBankingAccountRepository.findByAccountNo(Mockito.anyString())).thenReturn(businessBankingAccount);
    when(businessBankingAccountTransactionHistoryRepository.getTransactionHistoryList(Mockito.anyString())).thenReturn(
        generateBusinessAccountHistoryList(businessBankingAccount));

    BankingTransactionHistoryResponseDTO responseDTO = bankingAccountService.getBankStatementForLastThirtyDays(true, ACCOUNT_NO);

    Assert.assertEquals(ACCOUNT_NO, responseDTO.getAccountNo());
    Assert.assertEquals("Eyup Aksu", responseDTO.getAccountName());
    Assert.assertEquals(2, responseDTO.getBankingAccountTransactionHistoryList().size());

  }

  @Test
  public void shouldReturnOnlyBusinessAccountInfoAndHistory(){
    PersonalBankingAccount personalBankingAccount = new PersonalBankingAccount(new Person(), ACCOUNT_NO, "Eyup Aksu", Currency
        .getInstance("USD"), BigDecimal.valueOf(1000000), true);
    when(personalBankingAccountRepository.findByAccountNo(Mockito.anyString())).thenReturn(personalBankingAccount);
    when(personalBankingAccountTransactionHistoryRepository.getTransactionHistoryList(Mockito.anyString())).thenReturn(generatePersonalAccountHistoryList(personalBankingAccount));

    BankingTransactionHistoryResponseDTO responseDTO = bankingAccountService.getBankStatementForLastThirtyDays(true, ACCOUNT_NO);

    Assert.assertNull(responseDTO.getAccountNo());
    Assert.assertNull(responseDTO.getAccountName());
    Assert.assertNull(responseDTO.getBankingAccountTransactionHistoryList());

  }

  private List<PersonalBankingAccountTransactionHistory> generatePersonalAccountHistoryList(PersonalBankingAccount personalBankingAccount) {
    PersonalBankingAccountTransactionHistory transaction1 = new PersonalBankingAccountTransactionHistory(personalBankingAccount,TransactionType.OPEN_ACCOUNT.toString(),LocalDateTime.now().minusHours(1), BigDecimal.valueOf(10000));
    PersonalBankingAccountTransactionHistory transaction2= new PersonalBankingAccountTransactionHistory(personalBankingAccount, TransactionType.RECEIVED_MONEY.toString(),
        LocalDateTime.now(), BigDecimal.valueOf(1000));

    return Arrays.asList(transaction1,transaction2);
  }

  private List<BusinessBankingAccountTransactionHistory> generateBusinessAccountHistoryList(BusinessBankingAccount businessBankingAccount){
    BusinessBankingAccountTransactionHistory transaction1 = new BusinessBankingAccountTransactionHistory(businessBankingAccount, TransactionType.OPEN_ACCOUNT.toString(),
        LocalDateTime.now().minusHours(1), BigDecimal.valueOf(10000));

    BusinessBankingAccountTransactionHistory transaction2 = new BusinessBankingAccountTransactionHistory(businessBankingAccount, TransactionType.RECEIVED_MONEY.toString(),
        LocalDateTime.now(), BigDecimal.valueOf(1000));

    return Arrays.asList(transaction1,transaction2);
  }


}
