package com.revolut.moneytransferapi.service;

import com.revolut.moneytransferapi.EntityManagerUtil;
import com.revolut.moneytransferapi.domain.BankingAccount;
import com.revolut.moneytransferapi.domain.BusinessBankingAccount;
import com.revolut.moneytransferapi.domain.BusinessBankingAccountTransactionHistory;
import com.revolut.moneytransferapi.domain.PersonalBankingAccount;
import com.revolut.moneytransferapi.domain.PersonalBankingAccountTransactionHistory;
import com.revolut.moneytransferapi.domain.TransactionType;
import com.revolut.moneytransferapi.dto.MoneyTransferRequestDTO;
import com.revolut.moneytransferapi.dto.MoneyTransferResponseDTO;
import com.revolut.moneytransferapi.repository.BusinessBankingAccountRepository;
import com.revolut.moneytransferapi.repository.PersonalBankingAccountRepository;
import com.revolut.moneytransferapi.repository.implementation.BusinessBankingAccountRepositoryImpl;
import com.revolut.moneytransferapi.repository.implementation.PersonalBankingAccountRepositoryImpl;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;

public class MoneyTransferServiceImpl implements MoneyTransferService {

  private EntityManager entityManager = EntityManagerUtil.getEntityManager();
  //these repositories would be local instances but with this approach we can easily mock them.
  private BusinessBankingAccountRepository businessBankingAccountRepository = new BusinessBankingAccountRepositoryImpl();
  private PersonalBankingAccountRepository personalBankingAccountRepository = new PersonalBankingAccountRepositoryImpl();


  @Override
  public MoneyTransferResponseDTO moneyTransfer(MoneyTransferRequestDTO moneyTransferRequestDTO) {
    BankingAccount senderBankingAccount = findAccountByAccountIdAndAccountType(moneyTransferRequestDTO.isSenderBusinessAccount(), moneyTransferRequestDTO.getSenderAccountNo());
    if(senderBankingAccount.getAccountNo()==null){
      return new MoneyTransferResponseDTO(false,"Not found Sender Account");
    }

    if(senderBankingAccount.getBalance().compareTo(moneyTransferRequestDTO.getTransactionAmount())<0){
      return new MoneyTransferResponseDTO(false,"Not enough balance to send money");
    }

    BankingAccount receiverAccount = findAccountByAccountIdAndAccountType(moneyTransferRequestDTO.isReceiverBusinessAccount(),moneyTransferRequestDTO.getReceiverAccountNo());

    if(receiverAccount.getAccountNo()==null){
      return new MoneyTransferResponseDTO(false,"Not found Receiver Account");
    }
    entityManager.getTransaction().begin();

    calculateNewBalance(senderBankingAccount, TransactionType.SENT_MONEY, moneyTransferRequestDTO.getTransactionAmount());
    calculateNewBalance(receiverAccount, TransactionType.RECEIVED_MONEY, moneyTransferRequestDTO.getTransactionAmount());

    entityManager.getTransaction().commit();

    return new MoneyTransferResponseDTO(true, "succes");
  }

  private void calculateNewBalance(BankingAccount bankingAccount, TransactionType transactionType, BigDecimal transactionAmount) {
    if(transactionType.equals(TransactionType.SENT_MONEY)){
      bankingAccount.setBalance(bankingAccount.getBalance().subtract(transactionAmount));
      entityManager.merge(bankingAccount);
    }else if (transactionType.equals(TransactionType.RECEIVED_MONEY)){
      bankingAccount.setBalance(bankingAccount.getBalance().add(transactionAmount));
      entityManager.merge(bankingAccount);
    }
    addTransaction(bankingAccount, transactionType, transactionAmount);



  }

  private void addTransaction(BankingAccount bankingAccount, TransactionType transactionType, BigDecimal transactionAmount) {
    if(transactionType.equals(TransactionType.SENT_MONEY)){
      transactionAmount = transactionAmount.negate();
    }

    if(bankingAccount instanceof BusinessBankingAccount){
      entityManager.persist(new BusinessBankingAccountTransactionHistory((BusinessBankingAccount) bankingAccount,
          transactionType.toString(), LocalDateTime.now(), transactionAmount));
      return;
    }
    entityManager.persist(new PersonalBankingAccountTransactionHistory((PersonalBankingAccount) bankingAccount,
        transactionType.toString(), LocalDateTime.now(), transactionAmount));

  }

  private BankingAccount findAccountByAccountIdAndAccountType(boolean isBusinessAccount, String receiverAccountNo) {
    if(isBusinessAccount){
      return businessBankingAccountRepository.findByAccountNo(receiverAccountNo);
    }
    return personalBankingAccountRepository.findByAccountNo(receiverAccountNo);
  }

}
