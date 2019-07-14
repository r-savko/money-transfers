package com.revolut.money.transfer.db.mapper;

import com.revolut.money.transfer.model.Account;

import java.io.Serializable;
import java.util.Optional;

public interface AccountMapper extends EntityMapper<Account> {

    Optional<Account> readForUpdate(Serializable primaryKey);

}
