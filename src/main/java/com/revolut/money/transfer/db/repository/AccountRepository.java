package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.model.Account;
import org.apache.ibatis.session.SqlSessionManager;

public class AccountRepository extends AbstractRepository<Account, Long> {

    public AccountRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public Account create(Account entity) {
        return null;
    }

    @Override
    public Account read(Long id) {
        return null;
    }

    @Override
    public void update(Account entity) {

    }

    @Override
    public void delete(Account entity) {

    }


}
