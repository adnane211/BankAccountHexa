package fr.kata.bank.adapters.repository;

import fr.kata.bank.domain.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataAccountRepository extends MongoRepository<Account, String> {

}
