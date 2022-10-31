package fr.kata.bank.adapters;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.kata.bank.BankAccountHexaApplication;
import fr.kata.bank.adapters.rest.BankAccountResource;
import fr.kata.bank.adapters.rest.error.AccountErrorHandler;
import fr.kata.bank.domain.model.Account;
import fr.kata.bank.domain.model.Operation;
import fr.kata.bank.domain.model.enums.OperationType;
import fr.kata.bank.domain.port.primaire.AccountService;
import fr.kata.bank.domain.port.primaire.OperationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = {BankAccountHexaApplication.class})
public class BankAccountResourceTest {
    private static final String ACCOUNT_NUMBER = "123ABC";
    @Autowired
    AccountService accountService;

    @Autowired
    OperationService operationService;

    @Autowired
    private AccountErrorHandler errorHandler;

    private MockMvc mockMvc;

    BankAccountResource bankAccountResource;

    @BeforeEach
    public void before() {
        bankAccountResource = new BankAccountResource(accountService, operationService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bankAccountResource).setControllerAdvice(errorHandler).build();
    }

    @Test
    public void should_return_error_message_and_404_code_status() throws Exception {
        this.mockMvc.perform(get("/api/account/456ABC")
                        .accept(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void should_return_success_message_and_200_code_status() throws Exception {
        this.mockMvc.perform(get("/api/account/" + ACCOUNT_NUMBER)
                        .accept(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void should_return_account_details() throws Exception {
        Account account = new Account();
        account.setId("123ABC");
        account.setBalance(BigDecimal.valueOf(1000));
        account.setOperations(Collections.emptyList());
        accountService.save(account);
        mockMvc.perform(get("/api/account/" + ACCOUNT_NUMBER, account.getId())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.operations").isEmpty())
                .andExpect(jsonPath("$.balance").value(account.getBalance()));
    }

    @Test
    public void should_perform_a_deposit_operation() throws Exception {
        Account account = Account.builder().id(ACCOUNT_NUMBER).balance(BigDecimal.ZERO).operations(Collections.emptyList()).build();

        accountService.save(account);
        mockMvc.perform(put("/api/account/" + ACCOUNT_NUMBER + "/deposit", account.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectToJsonBytes(new Operation( ACCOUNT_NUMBER,  BigDecimal.ZERO, BigDecimal.valueOf(15000), LocalDateTime.now(), OperationType.DEPOSIT))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operations").isNotEmpty())
                .andExpect(jsonPath("$.balance").value(BigDecimal.valueOf(15000)));

    }

    @Test
    public void should_perform_a_withdrawal_operation() throws Exception {
        Account account = Account.builder().id(ACCOUNT_NUMBER).balance(BigDecimal.valueOf(15000)).operations(Collections.emptyList()).build();

        accountService.save(account);
        mockMvc.perform(put("/api/account/" + ACCOUNT_NUMBER + "/withdrawal", account.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectToJsonBytes(new Operation( ACCOUNT_NUMBER, BigDecimal.valueOf(15000),
                                BigDecimal.valueOf(5000), LocalDateTime.now(), OperationType.WITHDRAW))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operations").isNotEmpty())
                .andExpect(jsonPath("$.balance").value(BigDecimal.valueOf(10000)));

    }

    @Test
    public void should_show_all_previous_operations() throws Exception {
        Account account = Account.builder().id(ACCOUNT_NUMBER).balance(BigDecimal.ZERO).operations(Collections.emptyList()).build();
        accountService.save(account);
        Operation operation1 = new Operation();
        operation1.setAccountNumber(account.getId());
        operation1.setOperationType(OperationType.DEPOSIT);
        operation1.setAmount(BigDecimal.valueOf(500L));
        Operation operation2 = new Operation();
        operation2.setAccountNumber(account.getId());
        operation2.setOperationType(OperationType.DEPOSIT);
        operation2.setAmount(BigDecimal.valueOf(500L));
        account.setOperations(List.of(operation1, operation2));
        accountService.save(account);
        mockMvc.perform(get("/api/account/"+ ACCOUNT_NUMBER +"/history", account.getId())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*]").isNotEmpty())
                .andExpect(jsonPath("$.[*].id").value(hasItems(operation1.getId(),operation2.getId())))
                .andExpect(jsonPath("$.[*].amount").value(hasItems(operation1.getAmount().intValue(),operation2.getAmount().intValue())))
                .andExpect(jsonPath("$.[*].operationType").value(hasItems(operation1.getOperationType().toString(),operation2.getOperationType().toString())));
    }
    public static byte[] objectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsBytes(object);
    }
}
