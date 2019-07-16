package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.CurrencyMapper;
import com.revolut.money.transfer.model.Currency;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyRepositoryTest {

    private static final Long CURRENCY_ID = 1L;

    private static final Currency CURRENCY = new Currency().setCurrencyId(CURRENCY_ID).setCurrencyCode("USD");

    private CurrencyMapper currencyMapper;
    private CurrencyRepository currencyRepository;
    private SqlSessionManager sqlSessionManager = mock(SqlSessionManager.class);

    @BeforeEach
    void setup() {
        currencyRepository = new CurrencyRepository(sqlSessionManager);
        currencyMapper = mock(CurrencyMapper.class);
    }

    @Test
    void createTest() {
        assertThrows(UnsupportedOperationException.class, () -> currencyRepository.create(CURRENCY));
    }

    @Test
    void readTest() {
        assertThrows(UnsupportedOperationException.class, () -> currencyRepository.read(CURRENCY_ID));
    }

    @Test
    void updateTest() {
        assertThrows(UnsupportedOperationException.class, () -> currencyRepository.update(CURRENCY));
    }

    @Test
    void deleteTest() {
        assertThrows(UnsupportedOperationException.class, () -> currencyRepository.delete(CURRENCY_ID));
    }

    @Test
    void readByCurrencyCode() {
        when(sqlSessionManager.getMapper(CurrencyMapper.class)).thenReturn(currencyMapper);

        currencyRepository.readByCurrencyCode("USD");

        verify(currencyMapper).readByCurrencyCode(eq("USD"));
    }

}
