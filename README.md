# Money Transfer API

Money Transfer API has two functionality which is money sending and getting bank statement

## Requires

- Java (Least 8)
- Gradle


## How to Run

```groovy
gradle run
```

## Swagger
After running the project you can access swagger online documentation via link
[swagger-ui](http://localhost:8080/swagger-ui/)

## How to Test
The swagger API documentation is pretty useful for testing the API except for some failure scenarios that's why when you make any request and if you see unreasonable response please also try to make the same request via Postman or CURL

You can also access template requests in swagger document

Two Personal("11111111", "22222222") and one business ("33333333") accounts will be created after running the application for testing purposes.

You can also check h2-web UI via link [h2-web](http://localhost:7000/) to check the in-memory database after running the application
JDBC URL : jdbc:h2:mem:money-transfer
password : sa

You can check the money-transfer result with three API calls with this order

GET localhost:8080/api/v1/bank-statement/is-business/{isBusiness}/account-no/{accountNo}

POST /api/v1/money-transfer/money-transfer with this sample json
```json
{
  "senderAccountNo": "11111111",
  "receiverAccountNo": "22222222",
  "transactionAmount": 1000,
  "senderBusinessAccount": false,
  "receiverBusinessAccount": false
}
```
GET localhost:8080/api/v1/bank-statement/is-business/{isBusiness}/account-no/{accountNo}
