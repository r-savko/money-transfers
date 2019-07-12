package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.TransactionMapper;
import com.revolut.money.transfer.model.Transaction;
import org.apache.ibatis.session.SqlSessionManager;

public class TransactionRepository extends AbstractRepository<Transaction, Long> {

    public TransactionRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public Long create(Transaction entity) {
        return sessionManager.getMapper(TransactionMapper.class).create(entity);
    }

    @Override
    public Transaction read(Long id) {
        return null;
    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public void delete(Transaction entity) {

    }


}
