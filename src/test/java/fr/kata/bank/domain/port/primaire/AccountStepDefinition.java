package fr.kata.bank.domain.port.primaire;


import fr.kata.bank.domain.exception.AccountNotFoundException;
import fr.kata.bank.domain.exception.OperationException;
import fr.kata.bank.domain.model.Account;
import fr.kata.bank.domain.port.primaire.impl.AccountServiceImpl;
import fr.kata.bank.domain.port.secondaire.AccountRepository;
import fr.kata.bank.domain.port.secondaire.AccountRepositoryMock;
import fr.kata.bank.domain.model.enums.OperationType;
import io.cucumber.java8.En;
import io.cucumber.java8.Scenario;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountStepDefinition implements En {
    private AccountRepository accountRepository = new AccountRepositoryMock();
    private final AccountService accountService = new AccountServiceImpl(accountRepository);
    private Exception thrownException;
    private Account account = null;
    private Scenario scenario;

    public AccountStepDefinition() {

        Before((scenario) -> {
            if (account == null) {
                account = Account.builder().id("123ABC").balance(BigDecimal.ZERO).build();
                accountRepository.save(account);
            }
            this.scenario = scenario;
        });

        // Scenario 1 : Check
        Given("I'm checking my bank account {string}", (String string) -> {
            try {
                account = accountService.getAccount(string);
            } catch (AccountNotFoundException ex) {
                thrownException = ex;
            }
            assertNotNull(account);
        });

        // Scenario 3 : deposit
        Given("I deposit {int} euros in my account {string}", (Integer int1, String string) -> {
            try {
                account = accountService.doOperation(string, BigDecimal.valueOf(int1), LocalDateTime.now(), OperationType.DEPOSIT);
            } catch (OperationException ex) {
                thrownException = ex;
            }
        });

        // Scenario 4: withdraw
        Given("I withdraw {int} euros on my account {string}", (Integer int1, String string) -> {
            try {
                account.setBalance(BigDecimal.valueOf(500L));
                accountRepository.save(account);
                account = accountService.doOperation(string, BigDecimal.valueOf(int1), LocalDateTime.now(), OperationType.WITHDRAW);
            } catch (OperationException ex) {
                thrownException = ex;
            }
        });

        When("I check the balance of my account {string}", (String string) ->
            assertNotNull(account.getBalance()));

        Then("the balance should be {int}", (Integer int1) ->
                assertEquals(BigDecimal.valueOf(int1), account.getBalance()));

        // Scenario 5: Balance Enquiry
        Given("I print the statement of my account {string}", (String string) -> {
            account = accountService.doOperation(string, BigDecimal.valueOf(500), LocalDateTime.now(), OperationType.DEPOSIT);
            account = accountService.doOperation(string, BigDecimal.valueOf(250), LocalDateTime.now(), OperationType.WITHDRAW);
            accountRepository.findByAccountNumber(string).ifPresent(account -> {
                try {
                    account.printStatement(preparePrinter());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
        When("The statement is printed", () -> {
            String printed = new Scanner(new File("src/test/resources/print/printed-statement")).useDelimiter("\\Z").next();
            scenario.log(printed);
            assertNotNull(printed.trim());
        });

    }

    private PrintStream preparePrinter() throws IOException {
        Files.deleteIfExists(Paths.get("src/test/resources/print/printed-statement"));
        FileOutputStream output = new FileOutputStream("src/test/resources/print/printed-statement", true);
        return new PrintStream(output);
    }
}
