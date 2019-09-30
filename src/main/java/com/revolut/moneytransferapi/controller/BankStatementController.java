package com.revolut.moneytransferapi.controller;

import com.revolut.moneytransferapi.dto.BankingAccountTransactionHistoryResponseDTO;
import com.revolut.moneytransferapi.service.BankingAccountService;
import com.revolut.moneytransferapi.service.BankingAccountServiceImpl;
import io.swagger.annotations.Api;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("api/v1/bank-statement")
@Api(value = "/bank-statement")
public class BankStatementController {

  private BankingAccountService bankingAccountService = new BankingAccountServiceImpl();

  @GET
  @Path("is-business/{isBusiness}/account-no/{accountNo}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getBankStatement(
      @PathParam("isBusiness")boolean isBusiness,
      @PathParam("accountNo")String accountNo)
  {
    BankingAccountTransactionHistoryResponseDTO bankingTransactionHistoryResponse = bankingAccountService.getBankStatement(isBusiness, accountNo);

    if(bankingTransactionHistoryResponse.getAccountNo()==null){
      return Response.
          status(Status.NOT_FOUND)
          .build();
    }
    return Response.status(Status.OK).entity(bankingTransactionHistoryResponse).build();

  }
}
