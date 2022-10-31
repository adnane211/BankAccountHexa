package fr.kata.bank.adapters.rest;

import fr.kata.bank.domain.exception.AccountNotFoundException;
import fr.kata.bank.domain.exception.OperationException;
import fr.kata.bank.domain.model.Account;
import fr.kata.bank.domain.model.Operation;
import fr.kata.bank.domain.model.enums.OperationType;
import fr.kata.bank.domain.port.primaire.AccountService;
import fr.kata.bank.domain.port.primaire.OperationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class BankAccountResource {

    private final AccountService accountService;
    private final OperationService operationService;


    @ApiOperation(value = "getAccountStatement", notes = "Return the account state and operations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Account.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })

    @GetMapping("{accountId}")
    public ResponseEntity<Account> getAccountStatement(@PathVariable String accountId) throws AccountNotFoundException {
        try {
            return ResponseEntity.ok(accountService.getAccount(accountId));
        } catch (Exception ex) {
            throw new AccountNotFoundException("Account does not exists");
        }
    }

    @ApiOperation(value = "listOperations", notes = "lists all account's operations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", responseContainer = "list", response = Operation.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })
    @GetMapping("{accountId}/history")
    public List<Operation> listOperations(@PathVariable String accountId) throws AccountNotFoundException {
        return accountService.getAccount(accountId).getOperations();
    }


    @ApiOperation(value = "deposit", notes = "Perform a deposit on the given account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Account.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })
    @PutMapping(value = "{accountId}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable String accountId,
                                           @RequestBody Operation operation) throws AccountNotFoundException, OperationException {
        try {
            return ResponseEntity.ok(accountService.doOperation(accountId, operation.getAmount(), LocalDateTime.now(), OperationType.DEPOSIT));
        } catch (AccountNotFoundException ex) {
            throw new AccountNotFoundException("Account number " + accountId + " not found");
        } catch (OperationException ex) {
            throw new OperationException("The requested operation cannot be performed on the account number" + accountId + ".");
        }
    }


    @ApiOperation(value = "withdrawall", notes = "Perform a withdrawal on the given account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Account.class),
            @ApiResponse(code = 404, message = "Bad request"),
    })
    @PutMapping(value = "{accountId}/withdrawal")
    public ResponseEntity<Account> withdrawal(@PathVariable String accountId,
                                 @RequestBody Operation operation) throws AccountNotFoundException, OperationException {
        try {
            return ResponseEntity.ok(accountService.doOperation(accountId, operation.getAmount(), LocalDateTime.now(), OperationType.WITHDRAW));
        } catch (AccountNotFoundException ex) {
            throw new AccountNotFoundException("Account number " + accountId + " not found");
        } catch (OperationException ex) {
            throw new OperationException("The requested operation cannot be performed on the account number" + accountId + ".");
        }    }
}
