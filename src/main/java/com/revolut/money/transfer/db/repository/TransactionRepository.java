package com.revolut.money.transfer.db.repository;

import org.apache.ibatis.session.SqlSessionManager;

public class TransactionRepository extends AbstractRepository<TransactionRepository, Long> {

    public TransactionRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public Long persist(TransactionRepository entity) {
        return null;
    }

    @Override
    public TransactionRepository find(Long id) {
        return null;
    }

    @Override
    public Long update(TransactionRepository entity) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }
}
