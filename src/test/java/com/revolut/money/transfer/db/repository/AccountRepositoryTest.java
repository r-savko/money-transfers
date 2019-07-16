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
        Currency currency = new Currency().setCurrencyId(1L).setCurrencyCode("USD");
        Account account = new Account().setUserId(USER_ID).setAccountNumber("Test_1")
                .setCurrency(currency).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID);

        accountRepository.create(account);

        verify(accountMapper).create(eq(account));
    }

    @Test
    void readTest() {

        accountRepository.read(ACCOUNT_ID);

        verify(accountMapper).read(ACCOUNT_ID);

    }

    @Test
    void updateTest() {
        Currency currency = new Currency().setCurrencyId(1L).setCurrencyCode("USD");
        Account account = new Account().setUserId(USER_ID).setAccountNumber("Test_1")
                .setCurrency(currency).setBalance(BigDecimal.TEN).setAccountId(ACCOUNT_ID);

        accountRepository.update(account);

        verify(accountMapper).update(account);
    }

    @Test
    void deleteTest() {

        accountRepository.delete(ACCOUNT_ID);

        verify(accountMapper).delete(ACCOUNT_ID);

    }

    @Test
    void readForUpdate() {
        accountRepository.readForUpdate(ACCOUNT_ID);

        verify(accountMapper).readForUpdate(ACCOUNT_ID);

    }

}
