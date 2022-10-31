package fr.kata.bank.domain.port.secondaire;

import fr.kata.bank.domain.model.Operation;

import java.util.Optional;
import java.util.stream.Stream;

public interface OperationRepository {

    Optional<Operation> getOneById(String id);
}
