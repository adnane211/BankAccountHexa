package fr.kata.bank.adapters.repository;

import fr.kata.bank.domain.model.Operation;
import fr.kata.bank.domain.port.secondaire.OperationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class BankOperationRepository implements OperationRepository {

    private final SpringOperationRepository repository;

    public BankOperationRepository(SpringOperationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Operation> getOneById(String id) {
        return repository.findById(id);
    }
}
