package com.revolut.moneytransferapi.controller;

import com.revolut.moneytransferapi.dto.BankingTransactionHistoryResponseDTO;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("api/v1/account")
@Api(value = "/account")
public class BankingAccountController {

  private static final Logger logger = LoggerFactory.getLogger(BankingAccountController.class); 

  private BankingAccountService bankingAccountService = new BankingAccountServiceImpl();

  @GET
  @Path("is-business/{isBusiness}/account-no/{accountNo}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getBankStatementForLastThirtyDays(
      @PathParam("isBusiness")boolean isBusiness,
      @PathParam("accountNo")String accountNo)
  {
    BankingTransactionHistoryResponseDTO bankingTransactionHistoryResponse = bankingAccountService.getBankStatementForLastThirtyDays(isBusiness, accountNo);

    if(bankingTransactionHistoryResponse.getAccountName()==null){
      return Response.
          status(Status.NOT_FOUND).
          entity("")
          .build();
    }

    return Response.status(Status.OK).entity(bankingTransactionHistoryResponse).build();

  }
}
