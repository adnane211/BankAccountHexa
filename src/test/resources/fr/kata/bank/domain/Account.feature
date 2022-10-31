#noinspection: nonAsciiCharacters

Feature: Bank Account management

  @check
  Scenario: The user checking his back account
    Given I'm checking my bank account "123ABC"
    When I check the balance of my account "123ABC"
    Then the balance should be 0

  @deposit
  Scenario: Deposit 500 euros in my account
    Given I deposit 500 euros in my account "123ABC"
    When I check the balance of my account "123ABC"
    Then the balance should be 500

  @withdrawal
  Scenario: I withdraw 250 and check the balance
    Given I withdraw 250 euros on my account "123ABC"
    When I check the balance of my account "123ABC"
    Then the balance should be 250

  @balanceenquiry
  Scenario: I print the statement balance
    Given I print the statement of my account "123ABC"
    When The statement is printed
    Then the balance should be 250