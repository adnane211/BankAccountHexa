package fr.kata.bank.adapters.rest.error;

import fr.kata.bank.domain.exception.AccountNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AccountErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFoundException(final AccountNotFoundException accountNotFoundException, WebRequest wr) {
        return ResponseEntity.badRequest().body("No such account found for the given id \n" + accountNotFoundException.getMessage());
    }
}
