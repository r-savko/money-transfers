package com.revolut.money.transfer.resource;


import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.Currency;
import com.revolut.money.transfer.model.Transaction;
import com.revolut.money.transfer.service.AccountService;
import com.revolut.money.transfer.service.TransferService;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.GenericType;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class AccountResourceTest {

    private static final AccountService accountService = mock(AccountService.class);
    private static final TransferService transferService = mock(TransferService.class);

    private static final Long USER_ID = 1L;
    private static final Long ACCOUNT_ID = 1L;
    private static final String ENDPOINT_FIND_ACCOUNT = "/v1/account/1";
    private static final String ENDPOINT_DELETE_ACCOUNT = "/v1/account/1";
    private static final String ENDPOINT_FIND_ACCOUNT_TRANSACTIONS = "/v1/account/1/transactions";

    public static final ResourceExtension extension = ResourceExtension.builder()
            .addResource(new AccountResource(accountService, transferService))
            .build();

    @AfterEach
    public void tearDown() {
        reset(accountService);
        reset(transferService);
    }

    @Test
    public void findAccountTest() {
        // Given
        Currency currency = new Currency().setCurrencyId(1L).setCurrencyCode("USD");
        Account account = new Account().setUserId(USER_ID).setAccountNumber("Test_1")
                .setCurrency(currency).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID);

        when(accountService.findAccount(ACCOUNT_ID)).thenReturn(account);

        // When
        Account accountResponse = extension.target(ENDPOINT_FIND_ACCOUNT).request().get(Account.class);

        // Then
        assertThat(accountResponse).isEqualTo(account);
        verify(accountService).findAccount(ACCOUNT_ID);
    }

    @Test
    public void deleteAccountTest() {

        // When
        extension.target(ENDPOINT_DELETE_ACCOUNT).request().delete();

        // Then
        verify(accountService).deleteAccount(ACCOUNT_ID);
    }

    @Test
    public void findAccountTransactionsTest() {

        // Given
        List<Transaction> transactions = Lists.list(
                new Transaction().setTransactionId(1L).setAccount(ACCOUNT_ID)
                        .setAmount(BigDecimal.TEN).setMessage("General transaction info"),
                new Transaction().setTransactionId(2L).setAccount(ACCOUNT_ID)
                        .setAmount(BigDecimal.ONE).setMessage("General transaction info")
        );

        when(transferService.findAccountTransactions(ACCOUNT_ID)).thenReturn(transactions);

        // When
        List<Transaction> transactionsResponse = extension.target(ENDPOINT_FIND_ACCOUNT_TRANSACTIONS).request().get(
                new GenericType<List<Transaction>>() {
                }
        );

        // Then
        assertThat(transactionsResponse).asList().containsExactlyElementsOf(transactions);
        assertThat(transactionsResponse.size() == transactions.size());
        verify(transferService).findAccountTransactions(ACCOUNT_ID);
    }


}
