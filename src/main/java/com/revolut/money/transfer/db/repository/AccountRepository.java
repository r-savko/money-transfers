package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.AccountMapper;
import com.revolut.money.transfer.model.Account;
import org.apache.ibatis.session.SqlSessionManager;

import java.util.Optional;

public class AccountRepository extends AbstractRepository<Account, Long> {

    public AccountRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void create(Account entity) {
        sessionManager.getMapper(AccountMapper.class).create(entity);
    }

    @Override
    public Optional<Account> read(Long accountId) {
        return sessionManager.getMapper(AccountMapper.class).read(accountId);
    }

    @Override
    public void update(Account entity) {
        sessionManager.getMapper(AccountMapper.class).update(entity);
    }

    @Override
    public void delete(Long accountId) {
        sessionManager.getMapper(AccountMapper.class).delete(accountId);
    }

    public Optional<Account> readForUpdate(Long accountId) {
        return sessionManager.getMapper(AccountMapper.class).readForUpdate(accountId);
    }

}
