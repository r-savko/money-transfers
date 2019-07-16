package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.ExchangeRateMapper;
import com.revolut.money.transfer.model.ExchangeRate;
import org.apache.ibatis.session.SqlSessionManager;

import java.util.Optional;

/**
 * Repository to work with exchange rates
 */
public class ExchangeRateRepository extends AbstractRepository<ExchangeRate, Long> {

    public ExchangeRateRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void create(ExchangeRate entity) {
        throw new UnsupportedOperationException("Is not supported in the current application version");
    }

    @Override
    public Optional<ExchangeRate> read(Long currencyId) {
        throw new UnsupportedOperationException("Is not supported in the current application version");
    }

    @Override
    public void update(ExchangeRate entity) {
        throw new UnsupportedOperationException("Is not supported in the current application version");
    }

    @Override
    public void delete(Long currencyId) {
        throw new UnsupportedOperationException("Is not supported in the current application version");
    }

    public Optional<ExchangeRate> readExchangeRate(Long currencyIdFrom, Long currencyIdTo) {
        return sessionManager.getMapper(ExchangeRateMapper.class).readExchangeRate(currencyIdFrom, currencyIdTo);
    }


}
