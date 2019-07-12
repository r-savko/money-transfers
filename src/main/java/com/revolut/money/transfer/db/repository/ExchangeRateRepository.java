package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.ExchangeRateMapper;
import com.revolut.money.transfer.model.ExchangeRate;
import org.apache.ibatis.session.SqlSessionManager;

public class ExchangeRateRepository extends AbstractRepository<ExchangeRateRepository, Long> {

    public ExchangeRateRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public Long create(ExchangeRateRepository entity) {
        return null;
    }

    @Override
    public ExchangeRateRepository read(Long id) {
        return null;
    }

    @Override
    public void update(ExchangeRateRepository entity) {

    }

    @Override
    public void delete(ExchangeRateRepository entity) {

    }

    public ExchangeRate getExchangeRate(Long currencyIdFrom, Long currencyIdTo) {
        return sessionManager.getMapper(ExchangeRateMapper.class).getExchangeRate(currencyIdFrom, currencyIdTo);
    }


}
