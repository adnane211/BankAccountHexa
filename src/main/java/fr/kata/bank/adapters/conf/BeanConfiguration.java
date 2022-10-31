package fr.kata.bank.adapters.conf;

import fr.kata.bank.BankAccountHexaApplication;
import fr.kata.bank.adapters.repository.BankAccountRepository;
import fr.kata.bank.adapters.repository.BankOperationRepository;
import fr.kata.bank.domain.model.Account;
import fr.kata.bank.domain.port.primaire.AccountService;
import fr.kata.bank.domain.port.primaire.OperationService;
import fr.kata.bank.domain.port.primaire.impl.AccountServiceImpl;
import fr.kata.bank.domain.port.primaire.impl.OperationServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Collections;

@Configuration
@ComponentScan(basePackageClasses = BankAccountHexaApplication.class)
public class BeanConfiguration {

    @Bean
    AccountService accountService(BankAccountRepository repository) {
        return new AccountServiceImpl(repository);
    }

    @Bean
    OperationService operationService(BankOperationRepository repository) {
        return new OperationServiceImpl(repository);
    }

}
