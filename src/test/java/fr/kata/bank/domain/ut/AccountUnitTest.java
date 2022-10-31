package fr.kata.bank.domain.ut;

import fr.kata.bank.domain.exception.OperationException;
import fr.kata.bank.domain.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountUnitTest {

    @ParameterizedTest(name = "[{index} => accountNumber={0}, amount={1}, account={2}]")
    @MethodSource("fr.kata.bank.domain.ut.TestArgs#checkAccount")
    void should_return_balance_when_i_check_account(String accountNumber, BigDecimal amount, Account account) {
        assertEquals(account.getId(), accountNumber);
        assertEquals(account.getBalance(), amount);
    }

    @ParameterizedTest(name = "[{index} => accountNumber={0}, balance={1}, amount={2}, account={3}]")
    @MethodSource("fr.kata.bank.domain.ut.TestArgs#checkAccountAfterDeposit500")
    void should_return_the_balance_plus_500_when_i_deposit_500_in_my_account(String accountNumber, BigDecimal balance, BigDecimal amount, Account account) {
        account.deposit(accountNumber, amount, LocalDateTime.now());
        assertEquals(accountNumber, account.getId());
        assertEquals(balance.add(amount), account.getBalance());
        assertThat(account.getOperations().size()).isGreaterThan(0);
    }

    @ParameterizedTest(name = "[{index} => accountNumber={0}, balance={1}, amount={2}, account={3}]")
    @MethodSource("fr.kata.bank.domain.ut.TestArgs#checkAccountAfterWithdraw250")
    public void should_return_balance_250_because_i_withdraw_250_from_500(String accountNumber, BigDecimal balance, BigDecimal amount, Account account) {
        account.withdraw(accountNumber, amount, LocalDateTime.now());
        assertEquals(accountNumber, account.getId());
        assertEquals(balance.subtract(amount), account.getBalance());
        assertThat(account.getOperations().size()).isGreaterThan(0);
    }

    @ParameterizedTest(name = "[{index} => accountNumber={0}, balance={1}, amount={2}, account={3}]")
    @MethodSource("fr.kata.bank.domain.ut.TestArgs#checkAccountAfterWithdraw250_From_ZERO")
    public void should_throw_exception_Insufficient_credit_because_i_withdraw_250_from_0(String accountNumber, BigDecimal balance, BigDecimal amount, Account account) {
        OperationException thrown = Assertions.assertThrows(OperationException.class, () -> {
            account.withdraw(accountNumber, amount, LocalDateTime.now());
            assertEquals(accountNumber, account.getId());
            assertEquals(balance.subtract(amount), account.getBalance());
            assertThat(account.getOperations().size()).isGreaterThan(0);
        });

        assertEquals("Insufficient credit", thrown.getMessage());
    }
}
