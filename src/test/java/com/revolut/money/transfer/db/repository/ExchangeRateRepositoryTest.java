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
    private static final Long CURRENCY_TO = 1L;
    private static final Long CURRENCY_FROM = 2L;
    private static final BigDecimal RATE = BigDecimal.valueOf(1.5);
    private static final ExchangeRate EXCHANGE_RATE = new ExchangeRate()
            .setCurrencyFrom(CURRENCY_FROM).setCurrencyTo(CURRENCY_TO).setRate(RATE);

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
        // Given
        when(sqlSessionManager.getMapper(ExchangeRateMapper.class)).thenReturn(exchangeRateMapper);

        // When
        exchangeRateRepository.readExchangeRate(CURRENCY_FROM, CURRENCY_TO);

        // Then
        verify(exchangeRateMapper).readExchangeRate(eq(CURRENCY_FROM), eq(CURRENCY_TO));
    }

}
