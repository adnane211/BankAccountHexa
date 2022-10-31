# KATA BankAccount

#### This Kata is a project based on hexagonal architecture, using Maven, cucumber for BDD , Jupiter for TDD

### This Kata permits to realize the following tasks:
````
* Deposit and Withdrawal
* Account statement (date, amount, balance)
* Statement printing
  The scenarios tested in this project are :
````

### The Cucumber scenarios are: 
````
Scenario: The user checking his back account
Given I'm checking my bank account
And My bank account is empty
When I check the balance
Then the balance should be 0
````
````
Scenario: Deposit 500 euros in my account
Given I deposit 500 euros
When I check the balance
Then My balance should be 500
````
````
Scenario: I withdraw 250 and check the balance
Given I withdraw 250 euros
When I check the balance
Then My balance should be 250
````
````
Scenario: I print the statement balance
Given I print the statement
When The statement is printed
Then My balance should be 250
````

### This is a Spring-boot project exposing some REST endpoints in order to interact with the application.

##### The Endpoint to GET the account information :
````
/api/account/{accountId}
````
##### The response is exposed as follows :
````
{
    "id": "123ABC",
    "balance": 1500,
    "operations": []
}
````
##### The Endpoint to make a deposit :
````
/api/account/{accountId}/deposit
with a request body
{
  "accountNumber": "123ABC",
  "amount": 500,
  "dateTime": "2022-10-31T17:42:06.064Z",
  "operationType": "DEPOSIT"
}
````
##### The response is returned as follows :
````
{
  "id": "123ABC",
  "balance": 1500,
  "operations": [
    {
      "id": "0a7e7b8e-1e81-40d8-8a45-9e2539c637dc",
      "amount": 500,
      "dateTime": "2022-10-31T18:42:51.34",
      "operationType": "DEPOSIT",
      "balance": 500,
      "newBalance": 1000,
      "accountNumber": "123ABC"
    }
  ]
}
````

##### The Endpoint to make a withdrawal :
````
/api/account/{accountId}/withdrawal
with a request body
{
  "accountNumber": "123ABC",
  "amount": 500,
  "dateTime": "2022-10-31T19:42:00.001Z",
  "operationType": "WITHDRAW"
}
````
##### The response is returned as follows :
````
{
  "id": "123ABC",
  "balance": 1500,
  "operations": [
    {
      "id": "0a7e7b8e-1e81-40d8-8a45-9e2539c637dc",
      "amount": 500,
      "dateTime": "2022-10-31T18:42:51.34",
      "operationType": "DEPOSIT",
      "balance": 500,
      "newBalance": 1000,
      "accountNumber": "123ABC"
    },
    {
      "id": "7730276c-9533-4d0d-b4f2-1aaca9d50ec5",
      "amount": 500,
      "dateTime": "2022-10-31T18:43:02.028616",
      "operationType": "WITHDRAW",
      "balance": 1000,
      "newBalance": 500,
      "accountNumber": "123ABC"
    }
  ]
}
````

##### The Endpoint to get the history :
````
/api/account/{accountId}/history
````
##### The response is returned as follows :
````
{
  "id": "123ABC",
  "balance": 1500,
  "operations": [
    {
      "id": "0a7e7b8e-1e81-40d8-8a45-9e2539c637dc",
      "amount": 500,
      "dateTime": "2022-10-31T18:42:51.34",
      "operationType": "DEPOSIT",
      "balance": 500,
      "newBalance": 1000,
      "accountNumber": "123ABC"
    },
    {
      "id": "7730276c-9533-4d0d-b4f2-1aaca9d50ec5",
      "amount": 500,
      "dateTime": "2022-10-31T18:43:02.028616",
      "operationType": "WITHDRAW",
      "balance": 1000,
      "newBalance": 500,
      "accountNumber": "123ABC"
    },
    ...
  ]
}
````

### The application endpoints can be tested using Swagger-ui 
````
http://localhost:8080/swagger-ui/index.html
````

This application has a NoSQL embedded database (MongoDB) and a Test Account is initialized using CommandCLI at application startup.

