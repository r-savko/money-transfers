package com.revolut.money.transfer.service;

import com.revolut.money.transfer.db.repository.AccountRepository;
import com.revolut.money.transfer.db.repository.ExchangeRateRepository;
import com.revolut.money.transfer.db.repository.TransactionRepository;
import com.revolut.money.transfer.db.transaction.TransactionManager;
import com.revolut.money.transfer.exception.ApplicationException;
import com.revolut.money.transfer.model.*;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    private static final Long USER_ID_FROM = 1L;
    private static final Long USER_ID_TO = 1L;
    private static final String CURRENCY_CODE_1 = "USD";
    private static final String CURRENCY_CODE_2 = "EUR";
    private static final Long CURRENCY_ID_1 = 11L;
    private static final Long CURRENCY_ID_2 = 12L;
    private static final Long ACCOUNT_ID_FROM = 1L;
    private static final Long ACCOUNT_ID_TO = 2L;

    private static final Currency CURRENCY_1 = new Currency().setCurrencyId(CURRENCY_ID_1).setCurrencyCode(CURRENCY_CODE_1);
    private static final Currency CURRENCY_2 = new Currency().setCurrencyId(CURRENCY_ID_2).setCurrencyCode(CURRENCY_CODE_2);

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private ExchangeRateRepository exchangeRateRepository;
    private TransferService transferService;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        exchangeRateRepository = mock(ExchangeRateRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        SqlSessionManager sqlSessionManager = mock(SqlSessionManager.class);
        TransactionManager transactionManager = new TransactionManager(sqlSessionManager);
        transferService = new TransferService(
                transactionRepository, accountRepository, exchangeRateRepository, transactionManager
        );
    }

    @Test
    void successfulTransferSameCurrencyTest() {
        // Given
        Account accountFrom = new Account().setUserId(USER_ID_FROM).setAccountNumber("Test account number")
                .setCurrency(CURRENCY_1).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID_FROM);
        Account accountTo = new Account().setUserId(USER_ID_TO).setAccountNumber("Test account number")
                .setCurrency(CURRENCY_1).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID_TO);
        when(accountRepository.readForUpdate(ACCOUNT_ID_FROM)).thenReturn(Optional.of(accountFrom));
        when(accountRepository.readForUpdate(ACCOUNT_ID_TO)).thenReturn(Optional.of(accountTo));

        // When
        Transaction transaction = transferService.transfer(ACCOUNT_ID_FROM, ACCOUNT_ID_TO, BigDecimal.TEN);

        // Then
        assertThat(transaction.getAmount()).isEqualTo(BigDecimal.TEN);
        assertThat(transaction.getType()).isEqualByComparingTo(TransactionType.DEBIT);
        verify(transactionRepository, times(2)).create(any(Transaction.class));
        verify(accountRepository, times(2)).update(any(Account.class));
    }


    @Test
    void successfulTransferDifferentCurrencyTest() {
        // Given
        Account accountFrom = new Account().setUserId(USER_ID_FROM).setAccountNumber("Test account number")
                .setCurrency(CURRENCY_1).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID_FROM);
        Account accountTo = new Account().setUserId(USER_ID_TO).setAccountNumber("Test account number")
                .setCurrency(CURRENCY_2).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID_TO);
        when(accountRepository.readForUpdate(ACCOUNT_ID_FROM)).thenReturn(Optional.of(accountFrom));
        when(accountRepository.readForUpdate(ACCOUNT_ID_TO)).thenReturn(Optional.of(accountTo));
        BigDecimal exchangeRate = BigDecimal.valueOf(1.5);
        when(exchangeRateRepository.readExchangeRate(CURRENCY_ID_1, CURRENCY_ID_2)).thenReturn(
                Optional.of(new ExchangeRate().setRate(exchangeRate))
        );

        // When
        Transaction transaction = transferService.transfer(ACCOUNT_ID_FROM, ACCOUNT_ID_TO, BigDecimal.TEN);

        // Then
        assertThat(accountFrom.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(accountTo.getBalance()).isEqualByComparingTo(BigDecimal.valueOf(25));
        assertThat(transaction.getAmount()).isEqualByComparingTo(BigDecimal.TEN);
        assertThat(transaction.getType()).isEqualByComparingTo(TransactionType.DEBIT);
        verify(transactionRepository, times(2)).create(any(Transaction.class));
        verify(accountRepository, times(2)).update(any(Account.class));
    }

    @Test
    void sourceAccountNotFoundTransferTest() {
        // Given
        Account accountFrom = new Account().setUserId(USER_ID_FROM).setAccountNumber("Test account number")
                .setCurrency(CURRENCY_1).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID_FROM);
        Account accountTo = new Account().setUserId(USER_ID_TO).setAccountNumber("Test account number")
                .setCurrency(CURRENCY_2).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID_FROM);
        when(accountRepository.readForUpdate(ACCOUNT_ID_FROM)).thenReturn(Optional.empty());
        when(accountRepository.readForUpdate(ACCOUNT_ID_TO)).thenReturn(Optional.of(accountTo));

        // Then
        assertThrows(ApplicationException.class, () -> transferService.transfer(ACCOUNT_ID_FROM, ACCOUNT_ID_TO, BigDecimal.TEN));
        assertThat(accountFrom.getBalance()).isEqualByComparingTo(BigDecimal.TEN);
        assertThat(accountTo.getBalance()).isEqualByComparingTo(BigDecimal.TEN);
        verify(transactionRepository, never()).create(any(Transaction.class));
        verify(accountRepository, never()).update(any(Account.class));
    }

    @Test
    void destinationAccountNotFoundTransferTest() {
        // Given
        Account accountFrom = new Account().setUserId(USER_ID_FROM).setAccountNumber("Test account number")
                .setCurrency(CURRENCY_1).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID_FROM);
        Account accountTo = new Account().setUserId(USER_ID_TO).setAccountNumber("Test account number")
                .setCurrency(CURRENCY_2).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID_FROM);
        when(accountRepository.readForUpdate(ACCOUNT_ID_FROM)).thenReturn(Optional.of(accountFrom));
        when(accountRepository.readForUpdate(ACCOUNT_ID_TO)).thenReturn(Optional.empty());


        // Then
        assertThrows(ApplicationException.class, () -> transferService.transfer(ACCOUNT_ID_FROM, ACCOUNT_ID_TO, BigDecimal.TEN));
        assertThat(accountFrom.getBalance()).isEqualByComparingTo(BigDecimal.TEN);
        assertThat(accountTo.getBalance()).isEqualByComparingTo(BigDecimal.TEN);
        verify(transactionRepository, never()).create(any(Transaction.class));
        verify(accountRepository, never()).update(any(Account.class));
    }

    @Test
    void incorrectTransferAmountTest() {
        // Given
        Account accountFrom = new Account().setUserId(USER_ID_FROM).setAccountNumber("Test account number")
                .setCurrency(CURRENCY_1).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID_FROM);
        Account accountTo = new Account().setUserId(USER_ID_TO).setAccountNumber("Test account number")
                .setCurrency(CURRENCY_1).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID_FROM);
        when(accountRepository.readForUpdate(ACCOUNT_ID_FROM)).thenReturn(Optional.of(accountFrom));
        when(accountRepository.readForUpdate(ACCOUNT_ID_TO)).thenReturn(Optional.of(accountTo));

        // Then
        assertThrows(ApplicationException.class, () ->
                transferService.transfer(ACCOUNT_ID_FROM, ACCOUNT_ID_TO, BigDecimal.valueOf(20))
        );
        verify(transactionRepository, never()).create(any(Transaction.class));
        verify(accountRepository, never()).update(any(Account.class));
    }

    @Test
    void incorrectAccountBalanceTransferTest() {
        // Given
        Account accountFrom = new Account().setUserId(USER_ID_FROM).setAccountNumber("Test account number")
                .setCurrency(CURRENCY_1).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID_FROM);
        Account accountTo = new Account().setUserId(USER_ID_TO).setAccountNumber("Test account number")
                .setCurrency(CURRENCY_2).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID_FROM);
        when(accountRepository.readForUpdate(ACCOUNT_ID_FROM)).thenReturn(Optional.of(accountFrom));
        when(accountRepository.readForUpdate(ACCOUNT_ID_TO)).thenReturn(Optional.of(accountTo));

        // Then
        assertThrows(ApplicationException.class, () ->
                transferService.transfer(ACCOUNT_ID_FROM, ACCOUNT_ID_TO, BigDecimal.valueOf(20))
        );
        verify(transactionRepository, never()).create(any(Transaction.class));
        verify(accountRepository, never()).update(any(Account.class));
    }

    @Test
    void unknownExchangeRateTransferTest() {
        // Given
        Account accountFrom = new Account().setUserId(USER_ID_FROM).setAccountNumber("Test account number")
                .setCurrency(CURRENCY_1).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID_FROM);
        Account accountTo = new Account().setUserId(USER_ID_TO).setAccountNumber("Test account number")
                .setCurrency(CURRENCY_2).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID_FROM);
        when(accountRepository.readForUpdate(ACCOUNT_ID_FROM)).thenReturn(Optional.of(accountFrom));
        when(accountRepository.readForUpdate(ACCOUNT_ID_TO)).thenReturn(Optional.of(accountTo));
        when(exchangeRateRepository.readExchangeRate(CURRENCY_ID_1, CURRENCY_ID_2)).thenReturn(Optional.empty());

        // Then
        assertThrows(ApplicationException.class, () ->
                transferService.transfer(ACCOUNT_ID_FROM, ACCOUNT_ID_TO, BigDecimal.valueOf(5))
        );
        verify(transactionRepository, never()).create(any(Transaction.class));
        verify(accountRepository, never()).update(any(Account.class));
    }
}
