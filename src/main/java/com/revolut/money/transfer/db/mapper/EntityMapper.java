package com.revolut.money.transfer.db.mapper;

import java.io.Serializable;

public interface EntityMapper<T> {

    T create(T entity) ;

    T read(Serializable primaryKey);

    void update(T entity) ;

    void delete(T entity);
}
