package com.revolut.money.transfer.db.mapper;

import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.User;

import java.util.List;

public interface UserMapper extends EntityMapper<User> {

    List<Account> readUserAccounts(Long userId);

}
