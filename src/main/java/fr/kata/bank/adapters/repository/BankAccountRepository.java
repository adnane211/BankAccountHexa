package fr.kata.bank.adapters.repository;

import fr.kata.bank.domain.exception.AccountNotFoundException;
import fr.kata.bank.domain.model.Account;
import fr.kata.bank.domain.model.Operation;
import fr.kata.bank.domain.port.secondaire.AccountRepository;
import fr.kata.bank.domain.port.secondaire.OperationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class BankAccountRepository implements AccountRepository {

    private SpringDataAccountRepository repository;

    public BankAccountRepository(SpringDataAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Account> findByAccountNumber(String id) throws AccountNotFoundException {
        return repository.findById(id);
    }

    @Override
    public Account save(Account account) {
        return repository.save(account);
    }

    @Override
    public Stream<Account> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false);
    }

}
