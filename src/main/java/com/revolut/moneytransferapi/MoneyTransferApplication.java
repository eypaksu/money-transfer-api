package com.revolut.moneytransferapi;

import com.revolut.moneytransferapi.controller.HealthCheckController;
import com.revolut.moneytransferapi.domain.Address;
import com.revolut.moneytransferapi.domain.BusinessBankingAccount;
import com.revolut.moneytransferapi.domain.BusinessBankingAccountTransactionHistory;
import com.revolut.moneytransferapi.domain.Company;
import com.revolut.moneytransferapi.domain.CurrentSupportedCurrency;
import com.revolut.moneytransferapi.domain.Person;
import com.revolut.moneytransferapi.domain.PersonalBankingAccount;
import com.revolut.moneytransferapi.domain.TransactionType;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import javax.persistence.EntityManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class MoneyTransferApplication {

  public static void main(String[] args) throws Exception {

    org.h2.tools.Server.createWebServer(new String[]{"-web","-webAllowOthers","-webPort","7000"}).start();

    buildSwagger();

    Server jettyServer = new Server(8080);
    final HandlerList handlers = new HandlerList();

    handlers.addHandler( buildSwaggerUI() );
    handlers.addHandler( buildContext() );

    jettyServer.setHandler(handlers);

    initializeData();

    jettyServer.start();
    jettyServer.join();
  }

  private static ContextHandler buildContext()
  {
    ResourceConfig resourceConfig = new ResourceConfig();


    // Replace EntityBrowser with your resource class
    // io.swagger.jaxrs.listing loads up Swagger resources
    resourceConfig.packages( HealthCheckController.class.getPackage().getName(), ApiListingResource.class.getPackage().getName() );

    ServletContainer servletContainer = new ServletContainer( resourceConfig );
    ServletHolder entityBrowser = new ServletHolder( servletContainer );
    entityBrowser.setInitOrder(0);
    entityBrowser.setInitParameter(
        "jersey.config.server.provider.packages",
        "com.revolut.moneytransferapi.controller");
    ServletContextHandler entityBrowserContext = new ServletContextHandler( ServletContextHandler.SESSIONS );
    entityBrowserContext.setContextPath( "/" );
    entityBrowserContext.addServlet( entityBrowser, "/*" );

    return entityBrowserContext;
  }

  private static void buildSwagger()
  {
    // This configures Swagger
    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setVersion( "1.0.0" );
    beanConfig.setResourcePackage( HealthCheckController.class.getPackage().getName() );
    beanConfig.setScan( true );
    beanConfig.setBasePath( "/" );
    beanConfig.setDescription( "Money Transfer API" );
    beanConfig.setTitle( "Entity Browser" );
  }

  private static ContextHandler buildSwaggerUI() throws Exception
  {
    final ResourceHandler swaggerUIResourceHandler = new ResourceHandler();

    swaggerUIResourceHandler.setResourceBase( MoneyTransferApplication.class.getClassLoader().getResource( "swagger-ui" ).toURI().toString() );
    final ContextHandler swaggerUIContext = new ContextHandler();
    swaggerUIContext.setContextPath( "/docs/" );
    swaggerUIContext.setHandler( swaggerUIResourceHandler );

    return swaggerUIContext;
  }
  private static void initializeData(){
    EntityManager entityManager = EntityManagerUtil.getEntityManager();
    entityManager.getTransaction().begin();

    Address address = new Address();
    address.setAddress("33 Chamberlayne Road");
    address.setPostcode("NW103NB");

    entityManager.persist(address);

    Person person = new Person();
    person.setName("Merve Nur");
    person.setDateOfBirth(LocalDate.of(1990,10,12));
    person.setAddress(address);


    PersonalBankingAccount personalBankingAccount = new PersonalBankingAccount();
    personalBankingAccount.setPerson(person);
    personalBankingAccount.setAccountName("Merve NUr");
    personalBankingAccount.setId("123");
    personalBankingAccount.setBalance(BigDecimal.TEN.multiply(BigDecimal.valueOf(10000)));
    personalBankingAccount.setCurrency(Currency.getInstance(String.valueOf(CurrentSupportedCurrency.EUR)));

    entityManager.persist(personalBankingAccount);
    entityManager.persist(person);

    Company company = new Company();
    company.setAddress(address);
    company.setDateOfFound(LocalDate.of(2009, 10,10));
    company.setName("Apple");

    entityManager.persist(company);

    BusinessBankingAccount businessBankingAccount = new BusinessBankingAccount();
    businessBankingAccount.setCompany(company);
    businessBankingAccount.setAccountNo("12345678");
    businessBankingAccount.setId("124");

    entityManager.persist(businessBankingAccount);

    BusinessBankingAccountTransactionHistory businessBankingAccountTransactionHistory = new BusinessBankingAccountTransactionHistory();
    businessBankingAccountTransactionHistory.setBusinessBankingAccount(businessBankingAccount);
    businessBankingAccountTransactionHistory.setTransactionType(
        String.valueOf(TransactionType.OPEN_ACCOUNT));
    businessBankingAccountTransactionHistory.setTransactionTime(LocalDateTime.now());

    entityManager.persist(businessBankingAccountTransactionHistory);
    entityManager.getTransaction().commit();
  }



}
