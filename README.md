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