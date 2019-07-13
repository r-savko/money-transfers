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
    public Optional<Transaction> read(Long id) {
        return null;
    }

    @Override
    public void update(Transaction entity) {
        throw new UnsupportedOperationException("Transaction updating is not supported");
    }

    @Override
    public void delete(Long transactionId) {
        throw new UnsupportedOperationException("Transaction removing is not supported");
    }

    public List<Transaction> readUserIncomingTransactions(Long userId) {
        return sessionManager.getMapper(TransactionMapper.class).readUserIncomingTransactions(userId);
    }

    public List<Transaction> readUserOutgoingTransactions(Long userId) {
        return sessionManager.getMapper(TransactionMapper.class).readUserOutgoingTransactions(userId);
    }

}
