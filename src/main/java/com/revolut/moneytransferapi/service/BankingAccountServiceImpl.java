package com.revolut.moneytransferapi.service;

import com.revolut.moneytransferapi.domain.BankingAccountTransactionHistory;
import com.revolut.moneytransferapi.domain.BusinessBankingAccount;
import com.revolut.moneytransferapi.domain.PersonalBankingAccount;
import com.revolut.moneytransferapi.dto.BankingAccountTransactionHistoryResponseDTO;
import com.revolut.moneytransferapi.repository.BusinessBankingAccountRepository;
import com.revolut.moneytransferapi.repository.BusinessBankingAccountTransactionHistoryRepository;
import com.revolut.moneytransferapi.repository.PersonalBankingAccountRepository;
import com.revolut.moneytransferapi.repository.PersonalBankingAccountTransactionHistoryRepository;
import com.revolut.moneytransferapi.repository.implementation.BusinessBankingAccountRepositoryImpl;
import com.revolut.moneytransferapi.repository.implementation.BusinessBankingAccountTransactionHistoryRepositoryImpl;
import com.revolut.moneytransferapi.repository.implementation.PersonalBankingAccountRepositoryImpl;
import com.revolut.moneytransferapi.repository.implementation.PersonalBankingAccountTransactionHistoryRepositoryImpl;
import java.util.ArrayList;
import java.util.List;

public class BankingAccountServiceImpl implements BankingAccountService {

  //these repositories would be local instances but with this approach we can easily mock them.
  private BusinessBankingAccountTransactionHistoryRepository businessBankingAccountTransactionHistoryRepository=new BusinessBankingAccountTransactionHistoryRepositoryImpl();
  private BusinessBankingAccountRepository businessBankingAccountRepository = new BusinessBankingAccountRepositoryImpl();
  private PersonalBankingAccountTransactionHistoryRepository personalBankingAccountTransactionHistoryRepository = new PersonalBankingAccountTransactionHistoryRepositoryImpl();
  private PersonalBankingAccountRepository personalBankingAccountRepository = new PersonalBankingAccountRepositoryImpl();

  @Override
  public BankingAccountTransactionHistoryResponseDTO getBankStatement(boolean isBusiness, String accountNo) {
    if(isBusiness){
      return getBankStatementForBusinessAccount(accountNo);
    }
    return getBankStatementForPersonalAccount(accountNo);
  }


  private BankingAccountTransactionHistoryResponseDTO getBankStatementForBusinessAccount(String accountNo){
    BusinessBankingAccount businessBankingAccount = businessBankingAccountRepository.findByAccountNo(accountNo);
    if (businessBankingAccount.getAccountNo()==null){
      return new BankingAccountTransactionHistoryResponseDTO();
    }
    List<BankingAccountTransactionHistory> bankingAccountTransactionHistoryList= new ArrayList<>();
    bankingAccountTransactionHistoryList.addAll(businessBankingAccountTransactionHistoryRepository.getTransactionHistoryList(businessBankingAccount));
    return new BankingAccountTransactionHistoryResponseDTO(businessBankingAccount.getAccountNo(), businessBankingAccount.getAccountName(), businessBankingAccount.getBalance(),bankingAccountTransactionHistoryList);
 }

  private BankingAccountTransactionHistoryResponseDTO getBankStatementForPersonalAccount(String accountNo){
    PersonalBankingAccount personalBankingAccount = personalBankingAccountRepository.findByAccountNo(accountNo);
    if(personalBankingAccount.getAccountNo()==null){
      return new BankingAccountTransactionHistoryResponseDTO();
    }
    List<BankingAccountTransactionHistory> bankingAccountTransactionHistoryList= new ArrayList<>();
    bankingAccountTransactionHistoryList.addAll(personalBankingAccountTransactionHistoryRepository.getTransactionHistoryList(personalBankingAccount));
    return new BankingAccountTransactionHistoryResponseDTO(personalBankingAccount.getAccountNo(), personalBankingAccount.getAccountName(),personalBankingAccount.getBalance(), bankingAccountTransactionHistoryList);
  }

}
