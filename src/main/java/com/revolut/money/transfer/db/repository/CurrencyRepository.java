package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.CurrencyMapper;
import com.revolut.money.transfer.model.Currency;
import org.apache.ibatis.session.SqlSessionManager;

import java.util.Optional;

public class CurrencyRepository extends AbstractRepository<Currency, Long> {

    public CurrencyRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void create(Currency entity) {
        throw new UnsupportedOperationException("Is not supported in the current application version");
    }

    @Override
    public Optional<Currency> read(Long id) {
        throw new UnsupportedOperationException("Is not supported in the current application version");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Is not supported in the current application version");
    }

    @Override
    public void update(Currency entity) {
        throw new UnsupportedOperationException("Is not supported in the current application version");
    }

    public Optional<Currency> readByCurrencyCode(String currencyCode) {
        return sessionManager.getMapper(CurrencyMapper.class).readByCurrencyCode(currencyCode);
    }
}
