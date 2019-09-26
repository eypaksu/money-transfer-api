package com.revolut.moneytransferapi.controller;

import io.swagger.annotations.Api;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("api/v1/account")
@Api(value = "/account")
public class BankingAccountController {

  private static final Logger logger = LoggerFactory.getLogger(BankingAccountController.class); 

  @GET
  @Path("is-business/{isBusiness}/account-no/{accountNo}")
  @Produces(MediaType.TEXT_PLAIN)
  public Response test(
      @PathParam("is-business")boolean isBusiness,
      @PathParam("account-no")String accountNo)
  {
    return Response.ok().build();
  }
}
