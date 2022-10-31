package fr.kata.bank;

import fr.kata.bank.adapters.repository.BankAccountRepository;
import fr.kata.bank.domain.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Collections;

@SpringBootApplication
@Slf4j
public class BankAccountHexaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAccountHexaApplication.class, args);
    }

    @Bean
    public CommandLineRunner bootstrapData(BankAccountRepository repository) {
        log.info("Init Account 123ABC");
        return (args) ->
                repository.save(
                        Account.builder()
                                .id("123ABC")
                                .balance(BigDecimal.valueOf(500L)).operations(Collections.emptyList()).build());
    }

}
