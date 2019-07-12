package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.mapper.AccountMapper;
import com.revolut.money.transfer.model.Account;
import org.apache.ibatis.session.SqlSessionManager;

import java.util.List;

public class AccountRepository extends AbstractRepository<Account, Long> {

    public AccountRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void create(Account entity) {
    }

    @Override
    public Account read(Long id) {
        return sessionManager.getMapper(AccountMapper.class).read(id);
    }

    public List<Account> readAll() {
        return sessionManager.getMapper(AccountMapper.class).readAll();
    }

    public Account readForUpdate(Long id) {
        return sessionManager.getMapper(AccountMapper.class).readForUpdate(id);
    }

    @Override
    public void update(Account entity) {

    }

    @Override
    public void delete(Account entity) {

    }


}
