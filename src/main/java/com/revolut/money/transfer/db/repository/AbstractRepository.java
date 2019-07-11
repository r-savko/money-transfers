package com.revolut.money.transfer.db.repository;

import org.apache.ibatis.session.SqlSessionManager;


public abstract class AbstractRepository<T, N extends Number> {

    protected SqlSessionManager sessionManager;

    protected AbstractRepository(SqlSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public abstract N persist(T entity);

    public abstract T find(N id);

    public abstract  N update(T entity);

    public abstract N delete(N id);

}
