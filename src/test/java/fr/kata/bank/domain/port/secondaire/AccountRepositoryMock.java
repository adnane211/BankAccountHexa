package fr.kata.bank.domain.port.secondaire;

import fr.kata.bank.domain.model.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class AccountRepositoryMock implements AccountRepository {

    private final Map<String, Account> accounts;

    public AccountRepositoryMock() {
        accounts = new HashMap<>();
    }

    @Override
    public Optional<Account> findByAccountNumber(String id) {
        return Optional.ofNullable(accounts.get(id));
    }

    @Override
    public Account save(Account account) {
        accounts.put(account.getId(), account);
        return account;
    }

    @Override
    public Stream<Account> findAll() {
        return accounts.values().stream();
    }
}
