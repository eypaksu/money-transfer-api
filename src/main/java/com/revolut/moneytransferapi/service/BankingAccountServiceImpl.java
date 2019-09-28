package com.revolut.moneytransferapi.service;

import com.revolut.moneytransferapi.domain.BankingAccountTransactionHistory;
import com.revolut.moneytransferapi.domain.BusinessBankingAccount;
import com.revolut.moneytransferapi.domain.PersonalBankingAccount;
import com.revolut.moneytransferapi.dto.BankingTransactionHistoryResponseDTO;
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

  BusinessBankingAccountTransactionHistoryRepository businessBankingAccountTransactionHistoryRepository=new BusinessBankingAccountTransactionHistoryRepositoryImpl();

  BusinessBankingAccountRepository businessBankingAccountRepository = new BusinessBankingAccountRepositoryImpl();

  PersonalBankingAccountTransactionHistoryRepository personalBankingAccountTransactionHistoryRepository = new PersonalBankingAccountTransactionHistoryRepositoryImpl();

  PersonalBankingAccountRepository personalBankingAccountRepository = new PersonalBankingAccountRepositoryImpl();

  @Override
  public BankingTransactionHistoryResponseDTO getBankStatementForLastThirtyDays(boolean isBusiness, String accountNo) {
    if(isBusiness){
      return getBankStatementForBusinessAccount(accountNo);
    }
    return getBankStatementForPersonalAccount(accountNo);
  }

  private BankingTransactionHistoryResponseDTO getBankStatementForBusinessAccount(String accountNo){
    BusinessBankingAccount businessBankingAccount = businessBankingAccountRepository.findByAccountNo(accountNo);
    if (businessBankingAccount==null){
      return new BankingTransactionHistoryResponseDTO();
    }
    List<BankingAccountTransactionHistory> bankingAccountTransactionHistoryList= new ArrayList<>();
    bankingAccountTransactionHistoryList.addAll(businessBankingAccountTransactionHistoryRepository.getTransactionHistoryList(accountNo));
    return new BankingTransactionHistoryResponseDTO(businessBankingAccount.getAccountNo(), businessBankingAccount.getAccountName(),bankingAccountTransactionHistoryList);
  }

  private BankingTransactionHistoryResponseDTO getBankStatementForPersonalAccount(String accountNo){
    PersonalBankingAccount personalBankingAccount = personalBankingAccountRepository.findByAccountNo(accountNo);
    if(personalBankingAccount==null){
      return new BankingTransactionHistoryResponseDTO();
    }
    List<BankingAccountTransactionHistory> bankingAccountTransactionHistoryList= new ArrayList<>();
    bankingAccountTransactionHistoryList.addAll(personalBankingAccountTransactionHistoryRepository.getTransactionHistoryList(accountNo));
    return new BankingTransactionHistoryResponseDTO(personalBankingAccount.getAccountNo(), personalBankingAccount.getAccountName(),bankingAccountTransactionHistoryList);
  }



}
