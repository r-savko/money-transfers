package com.revolut.money.transfer.db.repository;

import org.apache.ibatis.session.SqlSessionManager;

public class ExchangeRateRepository extends AbstractRepository<ExchangeRateRepository, Long> {

    public ExchangeRateRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public ExchangeRateRepository create(ExchangeRateRepository entity) {
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


}
