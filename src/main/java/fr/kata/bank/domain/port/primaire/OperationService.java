package fr.kata.bank.domain.port.primaire;

import fr.kata.bank.domain.exception.OperationException;
import fr.kata.bank.domain.model.Operation;
import fr.kata.bank.domain.model.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface OperationService {

    Optional<Operation> findById(String id) throws OperationException;
    void operate(String accountNumber, BigDecimal balance, BigDecimal amount, LocalDateTime dateTime, OperationType operationType) throws OperationException;
}
