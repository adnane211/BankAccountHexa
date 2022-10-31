package fr.kata.bank.domain.model;

import fr.kata.bank.domain.exception.OperationException;
import fr.kata.bank.domain.model.enums.OperationType;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static fr.kata.bank.domain.model.enums.OperationType.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    private String id;
    private BigDecimal balance;
    @Builder.Default
    private List<Operation> operations = new ArrayList<>();


    public Account deposit(String id, BigDecimal amount, LocalDateTime dateTime) {
        operate(id, amount, dateTime, DEPOSIT);
        return this;
    }

    public Account withdraw(String id, BigDecimal amount, LocalDateTime dateTime) {
        operate(id, amount, dateTime, WITHDRAW);
        return this;
    }

    public void check(String id, BigDecimal amount, LocalDateTime dateTime) {
        operate(id, amount, dateTime, CHECK);
    }

    @SneakyThrows(OperationException.class)
    private void operate(String accountNumber, BigDecimal amount, LocalDateTime dateTime, OperationType operationType) {
        Operation operation = new Operation(accountNumber, this.balance, amount, dateTime, operationType);
        operation.operate();
        if (this.operations != null) {
            this.operations.add(operation);
        } else {
            this.operations = new ArrayList<>();
            this.operations.add(operation);
        }
        operation.setId(UUID.randomUUID().toString());
        this.balance = operation.getNewBalance();
    }

    public void printStatement(PrintStream printer) {

        StringBuilder builder = new StringBuilder();
        builder.append("Account ").append(id).append(" - Balance ").append(balance);
        builder.append(System.getProperty("line.separator"));
        builder.append("--------------------------------------------------------------------------------------");
        builder.append(System.getProperty("line.separator"));
        builder.append("Operation History            |");
        builder.append(System.getProperty("line.separator"));
        operations.stream().iterator().forEachRemaining(operation -> {
            builder.append("Previous balance: ")
                    .append(StringUtils.rightPad(operation.getBalance().toString(), 10))
                    .append(" | ")
                    .append(operation.getDateTime())
                    .append(" | ")
                    .append(operation.getOperationType().toString())
                    .append(" ")
                    .append(operation.getAmount().toString())
                    .append(" | ")
                    .append("Balance ")
                    .append(StringUtils.rightPad(operation.getNewBalance().toString(), 10));

            builder.append(System.getProperty("line.separator"));
        });
        builder.append("--------------------------------------------------------------------------------------");
        printer.println(builder);
    }

}
