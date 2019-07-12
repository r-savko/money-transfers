package com.revolut.money.transfer.service;

import com.revolut.money.transfer.db.repository.AccountRepository;
import com.revolut.money.transfer.model.Account;

import java.util.List;

public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccount(Long accountId) {
        return accountRepository.read(accountId);
    }

    public List<Account> getAllAccount() {
        return accountRepository.readAll();
    }

}
