package com.revolut.moneytransferapi.controller;

import com.revolut.moneytransferapi.dto.MoneyTransferRequestDTO;
import com.revolut.moneytransferapi.dto.MoneyTransferResponseDTO;
import com.revolut.moneytransferapi.service.MoneyTransferService;
import com.revolut.moneytransferapi.service.MoneyTransferServiceImpl;
import io.swagger.annotations.Api;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api/v1/money-transfer")
@Api(value = "/money-transfer")
public class MoneyTransferController {

  private MoneyTransferService bankingAccountService;

  @POST
  @Path("money-transfer")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response moneyTransfer(MoneyTransferRequestDTO moneyTransferDto){

    bankingAccountService = new MoneyTransferServiceImpl();
    MoneyTransferResponseDTO moneyTransferResponseDTO = bankingAccountService.moneyTransfer(moneyTransferDto);

    return Response.ok().build();
  }
}
