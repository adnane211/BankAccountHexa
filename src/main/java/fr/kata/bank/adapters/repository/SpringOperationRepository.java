package fr.kata.bank.adapters.repository;

import fr.kata.bank.domain.model.Operation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface SpringOperationRepository extends MongoRepository<Operation, String> {
}
