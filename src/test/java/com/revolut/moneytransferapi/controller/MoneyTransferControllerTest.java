package com.revolut.moneytransferapi.controller;

import static org.mockito.Mockito.when;

import com.revolut.moneytransferapi.constant.MoneyTransferConstant;
import com.revolut.moneytransferapi.dto.MoneyTransferRequestDTO;
import com.revolut.moneytransferapi.dto.MoneyTransferResponseDTO;
import com.revolut.moneytransferapi.service.MoneyTransferService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MoneyTransferControllerTest {

  @Mock
  private MoneyTransferService moneyTransferService;

  @InjectMocks
  private MoneyTransferController moneyTransferController;

  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldReturnNotFoundWhenSenderAccountDoesntExist(){
    when(moneyTransferService.moneyTransfer(Mockito
        .any())).thenReturn(new MoneyTransferResponseDTO(false, MoneyTransferConstant.NOT_FOUND_SENDER_ACCOUNT));

    Response response = moneyTransferController.sendMoney(new MoneyTransferRequestDTO());

    Assert.assertEquals(404, response.getStatus());
    Assert.assertEquals(Status.NOT_FOUND, response.getStatusInfo());
    Assert.assertEquals(MoneyTransferConstant.NOT_FOUND_SENDER_ACCOUNT, response.getEntity());

  }

  @Test
  public void shouldReturnNotFoundWhenReceiverAccountDoesntExist(){

    MoneyTransferRequestDTO moneyTransferRequestDTO = new MoneyTransferRequestDTO();
    when(moneyTransferService.moneyTransfer(moneyTransferRequestDTO)).thenReturn(new MoneyTransferResponseDTO(false, MoneyTransferConstant.NOT_FOUND_RECEIVER_ACCOUNT));

    Response response = moneyTransferController.sendMoney(moneyTransferRequestDTO);

    Assert.assertEquals(404, response.getStatus());
    Assert.assertEquals(Status.NOT_FOUND, response.getStatusInfo());
    Assert.assertEquals(MoneyTransferConstant.NOT_FOUND_RECEIVER_ACCOUNT, response.getEntity());

  }

  @Test
  public void shouldReturnPreConditionRequiredWhenSenderHasNoEnoughBalance(){
    MoneyTransferRequestDTO moneyTransferRequestDTO = new MoneyTransferRequestDTO();
    when(moneyTransferService.moneyTransfer(moneyTransferRequestDTO)).thenReturn(new MoneyTransferResponseDTO(false, MoneyTransferConstant.NOT_ENOUGH_BALANCE_TO_SEND_MONEY));

    Response response = moneyTransferController.sendMoney(moneyTransferRequestDTO);
    Assert.assertEquals(428, response.getStatus());
    Assert.assertEquals(Status.PRECONDITION_REQUIRED, response.getStatusInfo());

  }

  @Test
  public void shouldReturnOk(){
    MoneyTransferRequestDTO moneyTransferRequestDTO = new MoneyTransferRequestDTO();
    when(moneyTransferService.moneyTransfer(moneyTransferRequestDTO)).thenReturn(new MoneyTransferResponseDTO(true, MoneyTransferConstant.SUCCESSFUL_TRANSFER));

    Response response = moneyTransferController.sendMoney(moneyTransferRequestDTO);
    Assert.assertEquals(200, response.getStatus());
    Assert.assertEquals(Status.OK, response.getStatusInfo());

  }



}
