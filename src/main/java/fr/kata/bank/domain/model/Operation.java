package fr.kata.bank.domain.model;

import fr.kata.bank.domain.exception.OperationException;
import fr.kata.bank.domain.model.enums.OperationType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Operation {

    private String id;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private OperationType operationType;

    private BigDecimal balance;
    private BigDecimal newBalance;

    private String accountNumber;

    public Operation(String accountNumber, BigDecimal balance, BigDecimal amount, LocalDateTime dateTime, OperationType operationType) {
        this.balance = balance;
        this.amount = amount;
        this.dateTime = dateTime;
        this.operationType = operationType;
        this.accountNumber = accountNumber;
    }


    public void operate() throws OperationException {
        if (operationType == OperationType.CHECK) {
            this.newBalance = balance;
        } else if (operationType == OperationType.WITHDRAW) {
            if (this.balance.compareTo(amount) < 0) {
                throw new OperationException("Insufficient credit");
            } else {
                this.newBalance = balance.subtract(amount);
            }
        } else if (operationType == OperationType.DEPOSIT) {
            this.newBalance = balance.add(amount);
        } else {
            throw new OperationException("Unknown Operation " + operationType + " ");
        }
    }

}
