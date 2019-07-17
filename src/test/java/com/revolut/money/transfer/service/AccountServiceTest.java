package com.revolut.money.transfer.service;

import com.revolut.money.transfer.db.repository.AccountRepository;
import com.revolut.money.transfer.db.repository.CurrencyRepository;
import com.revolut.money.transfer.db.repository.TransactionRepository;
import com.revolut.money.transfer.db.transaction.TransactionManager;
import com.revolut.money.transfer.exception.ApplicationException;
import com.revolut.money.transfer.exception.NotFoundException;
import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.Currency;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    private static final Long USER_ID = 1L;
    private static final Long ACCOUNT_ID = 1L;
    private static final Long ACCOUNT_ID_2 = 2L;
    private static final String CURRENCY_CODE = "USD";
    private static final String ACCOUNT_NUMBER = "TEST_ACCOUNT_NUMBER";
    private static final Long CURRENCY_ID = 1L;
    private static final Currency CURRENCY = new Currency().setCurrencyId(CURRENCY_ID).setCurrencyCode(CURRENCY_CODE);

    private AccountRepository accountRepository;
    private CurrencyRepository currencyRepository;
    private TransactionRepository transactionRepository;
    private AccountService accountService;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        currencyRepository = mock(CurrencyRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        SqlSessionManager sqlSessionManager = mock(SqlSessionManager.class);
        TransactionManager transactionManager = new TransactionManager(sqlSessionManager);
        accountService = new AccountService(
                accountRepository, currencyRepository, transactionRepository, transactionManager
        );
    }

    @Test
    void findAccountTest() {
        // Given
        Account account = new Account().setUserId(USER_ID).setAccountNumber("Test_1")
                .setCurrency(CURRENCY).setBalance(BigDecimal.TEN).setAccountId(2L);

        when(accountRepository.read(ACCOUNT_ID)).thenReturn(Optional.of(account));

        // When
        Account accountResponse = accountService.findAccount(ACCOUNT_ID);

        // Then
        assertThat(accountResponse).isEqualTo(account);
        verify(accountRepository).read(ACCOUNT_ID);
    }

    @Test
    void findUnknownAccountTest() {
        // Given
        when(accountRepository.read(ACCOUNT_ID)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> accountService.findAccount(ACCOUNT_ID));
        verify(accountRepository).read(ACCOUNT_ID);
    }

    @Test
    void createAccountWithKnownCurrencyTest() {
        // Given
        when(currencyRepository.readByCurrencyCode(CURRENCY_CODE)).thenReturn(Optional.of(CURRENCY));

        // When
        Account resultAccount = accountService.createAccount(ACCOUNT_ID, CURRENCY_CODE, ACCOUNT_NUMBER);

        // Then
        verify(accountRepository).create(any(Account.class));
        assertThat(resultAccount.getBalance()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void createAccountWithUnknownCurrencyTest() {
        // Given
        when(currencyRepository.readByCurrencyCode(CURRENCY_CODE)).thenReturn(Optional.empty());

        // Then
        assertThrows(ApplicationException.class,
                () -> accountService.createAccount(ACCOUNT_ID, CURRENCY_CODE, ACCOUNT_NUMBER)
        );
        verify(accountRepository, never()).create(any(Account.class));
    }

    @Test
    void deleteAccountWithPositiveBalanceTest() {
        // Given
        Account account = new Account().setUserId(USER_ID).setAccountNumber("Test_1")
                .setCurrency(CURRENCY).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID);
        when(accountRepository.readForUpdate(ACCOUNT_ID)).thenReturn(Optional.of(account));

        // Then
        assertThrows(ApplicationException.class,
                () -> accountService.deleteAccount(ACCOUNT_ID)
        );
        verify(accountRepository, never()).delete(ACCOUNT_ID);

    }

    @Test
    void deleteAccountWithZeroBalanceTest() {
        // Given
        Account account = new Account().setUserId(USER_ID).setAccountNumber("Test_1")
                .setCurrency(CURRENCY).setBalance(BigDecimal.ZERO).setAccountId(ACCOUNT_ID_2);
        when(accountRepository.readForUpdate(ACCOUNT_ID)).thenReturn(Optional.of(account));

        // When
        accountService.deleteAccount(ACCOUNT_ID);

        // Then
        verify(transactionRepository).deleteAccountTransactions(ACCOUNT_ID);
        verify(accountRepository).delete(ACCOUNT_ID);
    }

    @Test
    void deleteUnknownAccountTest() {
        // Given
        when(accountRepository.readForUpdate(ACCOUNT_ID)).thenReturn(Optional.empty());

        // Then
        assertThrows(ApplicationException.class,
                () -> accountService.deleteAccount(ACCOUNT_ID)
        );
        verify(transactionRepository, never()).deleteAccountTransactions(ACCOUNT_ID);
        verify(accountRepository, never()).delete(ACCOUNT_ID);
    }

}
