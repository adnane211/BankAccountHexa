package fr.kata.bank.domain.ut;

import fr.kata.bank.domain.model.Account;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestArgs {

    static Stream<Arguments> checkAccount() {
        return Stream.of(
                arguments("123ABC", BigDecimal.ZERO, Account.builder().id("123ABC").balance(BigDecimal.ZERO).build()),
                arguments("456DEF", BigDecimal.valueOf(100.0), Account.builder().id("456DEF").balance(BigDecimal.valueOf(100.0)).build())
        );
    }

    static Stream<Arguments> checkAccountAfterDeposit500() {
        return Stream.of(
                arguments("123ABC", BigDecimal.ZERO, BigDecimal.valueOf(500), Account.builder().id("123ABC").balance(BigDecimal.ZERO).build()),
                arguments("456DEF", BigDecimal.valueOf(100.0), BigDecimal.valueOf(500), Account.builder().id("456DEF").balance(BigDecimal.valueOf(100.0)).build()),
                arguments("789HIJ", BigDecimal.valueOf(20), BigDecimal.valueOf(500), Account.builder().id("789HIJ").balance(BigDecimal.valueOf(20)).build())
        );
    }

    static Stream<Arguments> checkAccountAfterWithdraw250() {
        return Stream.of(
                arguments("123ABC", BigDecimal.valueOf(500), BigDecimal.valueOf(250), Account.builder().id("123ABC").balance(BigDecimal.valueOf(500L)).build())
        );
    }

    static Stream<Arguments> checkAccountAfterWithdraw250_From_ZERO() {
        return Stream.of(
                arguments("123ABC", BigDecimal.valueOf(500), BigDecimal.valueOf(250), Account.builder().id("123ABC").balance(BigDecimal.ZERO).build())
        );
    }
}
