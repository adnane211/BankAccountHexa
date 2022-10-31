package fr.kata.bank.domain.port.primaire.impl;

import fr.kata.bank.domain.exception.OperationException;
import fr.kata.bank.domain.model.Operation;
import fr.kata.bank.domain.model.enums.OperationType;
import fr.kata.bank.domain.port.primaire.OperationService;
import fr.kata.bank.domain.port.secondaire.OperationRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;

    @Override
    public Optional<Operation> findById(String id) throws OperationException {
        return Optional.ofNullable(operationRepository.getOneById(id))
                .orElseThrow(() -> new OperationException("No such operation with the given id " + id + ""));
    }

    @Override
    public void operate(String accountNumber, BigDecimal balance, BigDecimal amount, LocalDateTime dateTime, OperationType operationType) throws OperationException {
        Operation operation = new Operation(accountNumber, balance, amount, dateTime, operationType);
        operation.operate();
    }
}
