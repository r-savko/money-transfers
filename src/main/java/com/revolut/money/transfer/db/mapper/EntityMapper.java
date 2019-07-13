package com.revolut.money.transfer.db.mapper;

import java.io.Serializable;
import java.util.Optional;

public interface EntityMapper<T> {

    void create(T entity);

    Optional<T> read(Serializable primaryKey);

    void update(T entity);

    void delete(Serializable primaryKey);

}
