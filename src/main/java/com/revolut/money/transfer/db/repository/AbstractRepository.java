package com.revolut.money.transfer.db.repository;

import org.apache.ibatis.session.SqlSessionManager;

import java.util.Optional;

public abstract class AbstractRepository<T, N extends Number> {

    SqlSessionManager sessionManager;

    AbstractRepository(SqlSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public abstract void create(T entity);

    public abstract Optional<T> read(N id);

    public abstract void update(T entity);

    public abstract void delete(N id);

}
