package com.revolut.moneytransferapi.controller;

import com.google.common.collect.ImmutableSet;
import com.revolut.moneytransferapi.constant.MoneyTransferConstant;
import com.revolut.moneytransferapi.dto.MoneyTransferRequestDTO;
import com.revolut.moneytransferapi.dto.MoneyTransferResponseDTO;
import com.revolut.moneytransferapi.service.MoneyTransferService;
import com.revolut.moneytransferapi.service.MoneyTransferServiceImpl;
import io.swagger.annotations.Api;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("api/v1/money-transfer")
@Api(value = "/money-transfer")
public class MoneyTransferController {

  private static final Set<String> MESSAGES = ImmutableSet
      .of(MoneyTransferConstant.NOT_FOUND_SENDER_ACCOUNT,
          MoneyTransferConstant.NOT_FOUND_RECEIVER_ACCOUNT);
  private MoneyTransferService moneyTransferService = new MoneyTransferServiceImpl();

  @POST
  @Path("send-money")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response sendMoney(MoneyTransferRequestDTO moneyTransferDto){

    MoneyTransferResponseDTO moneyTransferResponseDTO = moneyTransferService.moneyTransfer(moneyTransferDto);

    if(!moneyTransferResponseDTO.isTransferResult()){
      if(MESSAGES.contains(moneyTransferResponseDTO.getMessage())){
         return Response.
            status(Status.NOT_FOUND)
             .entity(moneyTransferResponseDTO.getMessage())
            .build();
      } return Response.status(Status.PRECONDITION_REQUIRED)
          .entity(moneyTransferResponseDTO.getMessage())
          .build();
    }

    return Response.status(Status.OK).entity(moneyTransferDto).build();
  }
}
