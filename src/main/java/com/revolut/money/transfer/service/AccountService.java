package com.revolut.money.transfer.service;

import com.revolut.money.transfer.db.repository.AccountRepository;
import com.revolut.money.transfer.model.Account;

public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findAccount(Long accountId) {
        return accountRepository.read(accountId);
    }

}
