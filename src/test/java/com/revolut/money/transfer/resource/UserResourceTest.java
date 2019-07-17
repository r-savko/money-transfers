package com.revolut.money.transfer.resource;

import com.google.common.collect.Lists;
import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.Currency;
import com.revolut.money.transfer.model.User;
import com.revolut.money.transfer.resource.model.CreateAccountRequest;
import com.revolut.money.transfer.service.AccountService;
import com.revolut.money.transfer.service.UserService;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class UserResourceTest {

    private static final UserService userService = mock(UserService.class);
    private static final AccountService accountService = mock(AccountService.class);

    private static final Long USER_ID = 1L;
    private static final Long ACCOUNT_ID = 1L;
    private static final String CURRENCY_CODE = "USD";
    private static final Long CURRENCY_ID = 1L;
    private static final Currency CURRENCY = new Currency().setCurrencyId(CURRENCY_ID).setCurrencyCode(CURRENCY_CODE);
    private static final String ENDPOINT_FIND_USER = "/v1/user/1";
    private static final String ENDPOINT_CREATE_ACCOUNT = "/v1/user/1/account";
    private static final String ENDPOINT_FIND_USER_ACCOUNTS = "/v1/user/1/accounts";

    public static final ResourceExtension extension = ResourceExtension.builder()
            .addResource(new UserResource(userService, accountService))
            .build();

    @AfterEach
    void tearDown() {
        reset(userService);
        reset(accountService);
    }

    @Test
    void findUserTest() {
        // Given
        User user = new User().setUserId(USER_ID).setName("Name").setSurname("Surname");

        when(userService.findUser(USER_ID)).thenReturn(user);

        // When
        User userResponse = extension.target(ENDPOINT_FIND_USER).request().get(User.class);

        // Then
        assertThat(userResponse).isEqualTo(user);
        verify(userService).findUser(USER_ID);
    }

    @Test
    void findUserAccountsTest() {
        // Given
        Account firstAccount = new Account().setUserId(USER_ID).setAccountNumber("Test_1")
                .setCurrency(CURRENCY).setBalance(BigDecimal.valueOf(10)).setAccountId(ACCOUNT_ID);
        Account secondAccount = new Account().setUserId(USER_ID).setAccountNumber("Test_2")
                .setCurrency(CURRENCY).setBalance(BigDecimal.valueOf(10)).setAccountId(ACCOUNT_ID);

        List<Account> accounts = Lists.newArrayList(firstAccount, secondAccount);

        when(userService.findUserAccounts(USER_ID)).thenReturn(accounts);

        // When
        List<Account> accountsResult = extension.target(ENDPOINT_FIND_USER_ACCOUNTS).request().get(
                new GenericType<List<Account>>() {
                }
        );

        // Then
        assertThat(accountsResult).asList().containsExactlyElementsOf(accounts);
        assertThat(accountsResult.size() == accounts.size());
        verify(userService).findUserAccounts(USER_ID);

    }

    @Test
    void createAccountTest() {
        // Given
        Currency currency = new Currency().setCurrencyId(1L).setCurrencyCode("USD");
        Account account = new Account().setUserId(USER_ID).setAccountNumber("Test_1")
                .setCurrency(currency).setBalance(BigDecimal.TEN).setAccountId(2L);

        when(accountService.createAccount(anyLong(), anyString(), anyString())).thenReturn(account);

        CreateAccountRequest createAccountRequest = new CreateAccountRequest().setAccountNumber("TEST_NUMBER")
                .setCurrencyCode("USD");

        // When
        Account accountResponse = extension.target(ENDPOINT_CREATE_ACCOUNT).request().post(
                Entity.entity(createAccountRequest, MediaType.APPLICATION_JSON_TYPE), Account.class
        );

        // Then
        assertThat(accountResponse).isEqualTo(account);
        verify(accountService).createAccount(eq(USER_ID), eq("USD"), eq("TEST_NUMBER"));
    }
}

