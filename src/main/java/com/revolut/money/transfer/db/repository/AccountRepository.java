package com.revolut.money.transfer.db.repository;

import com.revolut.money.transfer.db.entity.Account;
import org.apache.ibatis.session.SqlSessionManager;

public class AccountRepository extends AbstractRepository<Account, Long> {

    public AccountRepository(SqlSessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public Long persist(Account entity) {
        return null;
    }

    @Override
    public Account find(Long id) {
        return null;
    }

    @Override
    public Long update(Account entity) {
        return null;
    }

    @Override
    public Long delete(Long id) {
        return null;
    }
}
