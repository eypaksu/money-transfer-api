package com.revolut.moneytransferapi.service;

import com.revolut.moneytransferapi.dto.MoneyTransferRequestDTO;
import com.revolut.moneytransferapi.dto.MoneyTransferResponseDTO;

public interface MoneyTransferService {
  MoneyTransferResponseDTO moneyTransfer(MoneyTransferRequestDTO moneyTransferRequestDTO);
}
