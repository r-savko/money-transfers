package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.ExchangeRateMapper;
import com.revolut.money.transfer.model.ExchangeRate;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRateRepositoryTest {

    private static final Long EXCHANGE_RATE_ID = 1L;

    private static final ExchangeRate EXCHANGE_RATE = new ExchangeRate()
            .setCurrencyFrom(1L).setCurrencyTo(1L).setRate(BigDecimal.valueOf(1.5));

    private ExchangeRateMapper exchangeRateMapper;
    private ExchangeRateRepository exchangeRateRepository;
    private SqlSessionManager sqlSessionManager = mock(SqlSessionManager.class);

    @BeforeEach
    void setup() {
        exchangeRateRepository = new ExchangeRateRepository(sqlSessionManager);
        exchangeRateMapper = mock(ExchangeRateMapper.class);
    }

    @Test
    void createTest() {
        assertThrows(UnsupportedOperationException.class, () -> exchangeRateRepository.create(EXCHANGE_RATE));
    }

    @Test
    void readTest() {
        assertThrows(UnsupportedOperationException.class, () -> exchangeRateRepository.read(EXCHANGE_RATE_ID));
    }

    @Test
    void updateTest() {
        assertThrows(UnsupportedOperationException.class, () -> exchangeRateRepository.update(EXCHANGE_RATE));
    }

    @Test
    void deleteTest() {
        assertThrows(UnsupportedOperationException.class, () -> exchangeRateRepository.delete(EXCHANGE_RATE_ID));
    }

    @Test
    void readExchangeRateTest() {
        when(sqlSessionManager.getMapper(ExchangeRateMapper.class)).thenReturn(exchangeRateMapper);

        exchangeRateRepository.readExchangeRate(1L, 2L);

        verify(exchangeRateMapper).readExchangeRate(eq(1L), eq(2L));
    }

}
