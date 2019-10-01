package com.revolut.moneytransferapi;

import com.revolut.moneytransferapi.controller.HealthCheckController;
import com.revolut.moneytransferapi.domain.Address;
import com.revolut.moneytransferapi.domain.BusinessBankingAccount;
import com.revolut.moneytransferapi.domain.BusinessBankingAccountTransactionHistory;
import com.revolut.moneytransferapi.domain.Company;
import com.revolut.moneytransferapi.domain.CurrentSupportedCurrency;
import com.revolut.moneytransferapi.domain.Person;
import com.revolut.moneytransferapi.domain.PersonalBankingAccount;
import com.revolut.moneytransferapi.domain.PersonalBankingAccountTransactionHistory;
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

    //you can check h2 db via this ling http://localhost:7000/
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
    swaggerUIContext.setContextPath( "/swagger-ui/" );
    swaggerUIContext.setHandler( swaggerUIResourceHandler );

    return swaggerUIContext;
  }
  private static void initializeData(){
    EntityManager entityManager = EntityManagerUtil.getEntityManager();
    entityManager.getTransaction().begin();

    Address address = new Address("33 Chamberlayne Road","NW103NB");

    entityManager.persist(address);

    Person person = new Person("Eyup","Aksu",LocalDate.of(1990,7,10),address);

    entityManager.persist(person);

    PersonalBankingAccount personalBankingAccount = new PersonalBankingAccount(person,"11111111","Eyup's Personal Account",
        Currency.getInstance(String.valueOf(CurrentSupportedCurrency.GBP)),BigDecimal.valueOf(10000),true);

    entityManager.persist(personalBankingAccount);

    PersonalBankingAccountTransactionHistory personalBankingAccountTransactionHistory0 = new PersonalBankingAccountTransactionHistory(personalBankingAccount,String.valueOf(TransactionType.OPEN_ACCOUNT), LocalDateTime.now().minusMonths(10), BigDecimal.valueOf(10000));
    PersonalBankingAccountTransactionHistory personalBankingAccountTransactionHistory1 = new PersonalBankingAccountTransactionHistory(personalBankingAccount,String.valueOf(TransactionType.SENT_MONEY), LocalDateTime.now().minusMonths(10), BigDecimal.valueOf(100).negate());
    PersonalBankingAccountTransactionHistory personalBankingAccountTransactionHistory2 = new PersonalBankingAccountTransactionHistory(personalBankingAccount,String.valueOf(TransactionType.RECEIVED_MONEY), LocalDateTime.now().minusMonths(10), BigDecimal.valueOf(100));

    entityManager.persist(personalBankingAccountTransactionHistory0);
    entityManager.persist(personalBankingAccountTransactionHistory1);
    entityManager.persist(personalBankingAccountTransactionHistory2);

    PersonalBankingAccount personalBankingAccount1 = new PersonalBankingAccount(person,"22222222","Eyup's Second Personal Account",
        Currency.getInstance(CurrentSupportedCurrency.GBP.toString()),BigDecimal.valueOf(10000),true);

    entityManager.persist(personalBankingAccount1);

    PersonalBankingAccountTransactionHistory personalBankingAccountTransactionHistory3 = new PersonalBankingAccountTransactionHistory(personalBankingAccount1,String.valueOf(TransactionType.OPEN_ACCOUNT), LocalDateTime.now().minusMonths(10), BigDecimal.valueOf(10000));
    PersonalBankingAccountTransactionHistory personalBankingAccountTransactionHistory4 = new PersonalBankingAccountTransactionHistory(personalBankingAccount1,String.valueOf(TransactionType.SENT_MONEY), LocalDateTime.now().minusMonths(10), BigDecimal.valueOf(100).negate());
    PersonalBankingAccountTransactionHistory personalBankingAccountTransactionHistory5 = new PersonalBankingAccountTransactionHistory(personalBankingAccount1,String.valueOf(TransactionType.RECEIVED_MONEY), LocalDateTime.now().minusMonths(10), BigDecimal.valueOf(100));

    entityManager.persist(personalBankingAccountTransactionHistory3);
    entityManager.persist(personalBankingAccountTransactionHistory4);
    entityManager.persist(personalBankingAccountTransactionHistory5);


    Company company = new Company("Apple",LocalDate.of(1990,10,10), address);

    entityManager.persist(company);

    BusinessBankingAccount businessBankingAccount = new BusinessBankingAccount(company,"33333333","Apple",Currency.getInstance(
        String.valueOf(CurrentSupportedCurrency.GBP)),BigDecimal.valueOf(1100000),true);

    entityManager.persist(businessBankingAccount);

    BusinessBankingAccountTransactionHistory businessBankingAccountTransactionHistory = new BusinessBankingAccountTransactionHistory(businessBankingAccount,String.valueOf(TransactionType.OPEN_ACCOUNT),LocalDateTime.now().minusYears(1),BigDecimal.valueOf(1000000));
    BusinessBankingAccountTransactionHistory businessBankingAccountTransactionHistory1 = new BusinessBankingAccountTransactionHistory(businessBankingAccount,String.valueOf(TransactionType.SENT_MONEY),LocalDateTime.now().minusMonths(10),BigDecimal.valueOf(10000).negate());
    BusinessBankingAccountTransactionHistory businessBankingAccountTransactionHistory2 = new BusinessBankingAccountTransactionHistory(businessBankingAccount,String.valueOf(TransactionType.RECEIVED_MONEY),LocalDateTime.now().minusMonths(9),BigDecimal.valueOf(10000));
    BusinessBankingAccountTransactionHistory businessBankingAccountTransactionHistory3 = new BusinessBankingAccountTransactionHistory(businessBankingAccount,String.valueOf(TransactionType.RECEIVED_MONEY),LocalDateTime.now(),BigDecimal.valueOf(100000));

    entityManager.persist(businessBankingAccountTransactionHistory);
    entityManager.persist(businessBankingAccountTransactionHistory1);
    entityManager.persist(businessBankingAccountTransactionHistory2);
    entityManager.persist(businessBankingAccountTransactionHistory3);


    entityManager.getTransaction().commit();
  }



}
