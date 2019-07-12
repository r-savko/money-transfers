package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.TransactionMapper;
import com.revolut.money.transfer.model.Transaction;
import org.apache.ibatis.session.SqlSessionManager;

import java.util.List;

public class TransactionRepository extends AbstractRepository<Transaction, Long> {

    public TransactionRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void create(Transaction entity) {
        sessionManager.getMapper(TransactionMapper.class).create(entity);
    }

    @Override
    public Transaction read(Long id) {
        return null;
    }

    public List<Transaction> readAll(){
        return sessionManager.getMapper(TransactionMapper.class).readAll();
    }

    @Override
    public void update(Transaction entity) {
        throw new UnsupportedOperationException("Transaction updating is not supported");
    }

    @Override
    public void delete(Transaction entity) {
        throw new UnsupportedOperationException("Transaction removing is not supported");
    }

}
