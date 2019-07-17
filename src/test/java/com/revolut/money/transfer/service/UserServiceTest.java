package com.revolut.money.transfer.service;

import com.revolut.money.transfer.db.repository.UserRepository;
import com.revolut.money.transfer.exception.NotFoundException;
import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.Currency;
import com.revolut.money.transfer.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Long USER_ID = 1L;
    private static final Long ACCOUNT_ID = 123L;
    private static final String CURRENCY_CODE = "USD";
    private static final Long CURRENCY_ID = 1L;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findKnownUserTest() {
        // Given
        User user = new User().setUserId(USER_ID).setName("Name").setSurname("Surname");
        when(userRepository.read(USER_ID)).thenReturn(Optional.of(user));

        // When
        User userResult = userService.findUser(USER_ID);

        // Then
        assertThat(userResult).isEqualTo(user);
        verify(userRepository).read(USER_ID);
    }

    @Test
    void findUnknownUserTest() {
        // Given
        when(userRepository.read(USER_ID)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> userService.findUser(USER_ID));
        verify(userRepository).read(USER_ID);
    }

    @Test
    void findUserAccountsTest() {
        // Given
        Currency currency = new Currency().setCurrencyId(CURRENCY_ID).setCurrencyCode(CURRENCY_CODE);
        Account firstAccount = new Account().setUserId(USER_ID).setAccountNumber("Test_1")
                .setCurrency(currency).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID);
        Account secondAccount = new Account().setUserId(USER_ID).setAccountNumber("Test_2")
                .setCurrency(currency).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID);

        List<Account> accounts = List.of(firstAccount, secondAccount);

        when(userRepository.readUserAccounts(USER_ID)).thenReturn(accounts);

        // When
        List<Account> accountsResult = userService.findUserAccounts(USER_ID);

        // Then
        assertThat(accountsResult).asList().containsExactlyElementsOf(accounts);
        assertThat(accountsResult.size() == accounts.size());
        verify(userRepository).readUserAccounts(USER_ID);

    }

}
