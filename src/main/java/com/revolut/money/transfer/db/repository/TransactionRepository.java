package com.revolut.money.transfer.db.repository;

import org.apache.ibatis.session.SqlSessionManager;

public class TransactionRepository extends AbstractRepository<TransactionRepository, Long> {

    public TransactionRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public TransactionRepository create(TransactionRepository entity) {
        return null;
    }

    @Override
    public TransactionRepository read(Long id) {
        return null;
    }

    @Override
    public void update(TransactionRepository entity) {

    }

    @Override
    public void delete(TransactionRepository entity) {

    }


}
