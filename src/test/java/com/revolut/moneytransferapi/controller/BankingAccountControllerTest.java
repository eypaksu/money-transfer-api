package com.revolut.moneytransferapi.controller;

import static org.mockito.Mockito.when;

import com.revolut.moneytransferapi.domain.BankingAccountTransactionHistory;
import com.revolut.moneytransferapi.domain.BusinessBankingAccount;
import com.revolut.moneytransferapi.domain.BusinessBankingAccountTransactionHistory;
import com.revolut.moneytransferapi.domain.Company;
import com.revolut.moneytransferapi.domain.TransactionType;
import com.revolut.moneytransferapi.dto.BankingTransactionHistoryResponseDTO;
import com.revolut.moneytransferapi.service.BankingAccountService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class BankingAccountControllerTest {

  @InjectMocks
  private BankingAccountController bankingAccountController;

  @Mock
  private BankingAccountService bankingAccountService;

  private static final String ACCOUNT_NO = "00000000000";

  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldReturnNotFoundIfAccountDoesntExist(){
    Mockito.doReturn(new BankingTransactionHistoryResponseDTO()).when(bankingAccountService.getBankStatementForLastThirtyDays(Mockito.anyBoolean(),Mockito.anyString()));

    Response response = bankingAccountController.getBankStatementForLastThirtyDays(false,ACCOUNT_NO);

    Assert.assertEquals(404, response.getStatus());
    Assert.assertEquals(Status.NOT_FOUND, response.getStatusInfo());

  }

  @Test
  public void shouldReturnTransactionHistory(){
    Mockito.doReturn(generateBankingTransactionHistoryResponse()).when(bankingAccountService).getBankStatementForLastThirtyDays(Mockito.anyBoolean(),Mockito.anyString());

    Response response = bankingAccountController.getBankStatementForLastThirtyDays(
        true,ACCOUNT_NO);

    Assert.assertEquals(200, response.getStatus());
    Assert.assertEquals(Status.OK, response.getStatusInfo());
  }


  private BankingTransactionHistoryResponseDTO generateBankingTransactionHistoryResponse(){
    BusinessBankingAccount businessBankingAccount = new BusinessBankingAccount(new Company(),"12345678","Eyup Aksu", Currency.getInstance("EUR"),BigDecimal.valueOf(1000), true);
    BusinessBankingAccountTransactionHistory transaction1 = new BusinessBankingAccountTransactionHistory(businessBankingAccount, TransactionType.OPEN_ACCOUNT.toString(),
        LocalDateTime.now().minusHours(1), BigDecimal.valueOf(10000));

    BusinessBankingAccountTransactionHistory transaction2 = new BusinessBankingAccountTransactionHistory(businessBankingAccount, TransactionType.RECEIVED_MONEY.toString(),
        LocalDateTime.now(), BigDecimal.valueOf(1000));

    List<BankingAccountTransactionHistory> transactionHistoryList = Arrays.asList(transaction1,transaction2);

    BankingTransactionHistoryResponseDTO responseDTO = new BankingTransactionHistoryResponseDTO();
    responseDTO.setBankingAccountTransactionHistoryList(transactionHistoryList);
    responseDTO.setAccountNo(businessBankingAccount.getAccountNo());
    responseDTO.setAccountName(businessBankingAccount.getAccountName());

    return responseDTO;
  }

}
