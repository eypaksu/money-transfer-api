package com.revolut.moneytransferapi.controller;

import io.swagger.annotations.Api;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("api/v1/health-check")
@Api(value = "/health-check")
public class HealthCheckController {

  @GET
  @Path("/test")
  @Produces(MediaType.TEXT_PLAIN)
  public String test() {
    return "--------------------------===============";
  }

}
