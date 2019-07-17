package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.AccountMapper;
import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.Currency;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryTest {

    private static final Long USER_ID = 1L;
    private static final Long ACCOUNT_ID = 1L;
    private static final String CURRENCY_CODE = "USD";
    private static final Long CURRENCY_ID = 1L;
    private static final Currency CURRENCY = new Currency().setCurrencyId(CURRENCY_ID).setCurrencyCode(CURRENCY_CODE);
    private static final Account ACCOUNT = new Account().setUserId(USER_ID).setAccountNumber("Test number")
            .setCurrency(CURRENCY).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID);

    private AccountRepository accountRepository;
    private AccountMapper accountMapper;

    @BeforeEach
    void setup() {
        SqlSessionManager sqlSessionManager = mock(SqlSessionManager.class);
        accountRepository = new AccountRepository(sqlSessionManager);
        accountMapper = mock(AccountMapper.class);
        when(sqlSessionManager.getMapper(AccountMapper.class)).thenReturn(accountMapper);
    }

    @Test
    void createTest() {
        // When
        accountRepository.create(ACCOUNT);

        // Then
        verify(accountMapper).create(eq(ACCOUNT));
    }

    @Test
    void readTest() {
        // When
        accountRepository.read(ACCOUNT_ID);

        // Then
        verify(accountMapper).read(ACCOUNT_ID);
    }

    @Test
    void updateTest() {
        // When
        accountRepository.update(ACCOUNT);

        // Then
        verify(accountMapper).update(ACCOUNT);
    }

    @Test
    void deleteTest() {
        // When
        accountRepository.delete(ACCOUNT_ID);

        // Then
        verify(accountMapper).delete(ACCOUNT_ID);
    }

    @Test
    void readForUpdate() {
        // When
        accountRepository.readForUpdate(ACCOUNT_ID);

        // Then
        verify(accountMapper).readForUpdate(ACCOUNT_ID);
    }

}
