package com.revolut.money.transfer.db.repository;

import org.apache.ibatis.session.SqlSessionManager;

public class ExchangeRateRepository extends AbstractRepository<ExchangeRateRepository, Long> {

    public ExchangeRateRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public Long persist(ExchangeRateRepository entity) {
        return null;
    }

    @Override
    public ExchangeRateRepository find(Long id) {
        return null;
    }

    @Override
    public Long update(ExchangeRateRepository entity) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }
}
