package com.revolut.money.transfer.db.repository;

import org.apache.ibatis.session.SqlSessionManager;

public abstract class AbstractRepository<T, N extends Number> {

    SqlSessionManager sessionManager;

    AbstractRepository(SqlSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public abstract N create(T entity);

    public abstract T read(N id);

    public abstract void update(T entity);

    public abstract void delete(T entity);

}
