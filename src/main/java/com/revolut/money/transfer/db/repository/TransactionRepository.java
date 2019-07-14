package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.TransactionMapper;
import com.revolut.money.transfer.model.Transaction;
import org.apache.ibatis.session.SqlSessionManager;

import java.util.List;
import java.util.Optional;

public class TransactionRepository extends AbstractRepository<Transaction, Long> {

    public TransactionRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void create(Transaction entity) {
        sessionManager.getMapper(TransactionMapper.class).create(entity);
    }

    @Override
    public Optional<Transaction> read(Long transactionId) {
        return sessionManager.getMapper(TransactionMapper.class).read(transactionId);
    }

    @Override
    public void update(Transaction entity) {
        throw new UnsupportedOperationException("Transaction update is not supported");
    }

    @Override
    public void delete(Long transactionId) {
        sessionManager.getMapper(TransactionMapper.class).delete(transactionId);
    }

    public void deleteAccountTransactions(Long transactionId) {
        sessionManager.getMapper(TransactionMapper.class).deleteAccountTransactions(transactionId);
    }

    public List<Transaction> readAccountTransactions(Long accountId) {
        return sessionManager.getMapper(TransactionMapper.class).readAccountTransactions(accountId);
    }

}
