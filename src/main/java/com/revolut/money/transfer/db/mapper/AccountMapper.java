package com.revolut.money.transfer.db.mapper;

import com.revolut.money.transfer.model.Account;

import java.io.Serializable;

public interface AccountMapper extends EntityMapper<Account> {

    Account readForUpdate(Serializable primaryKey);

}
