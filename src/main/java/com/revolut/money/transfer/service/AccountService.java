package com.revolut.money.transfer.service;

import com.revolut.money.transfer.db.repository.AccountRepository;
import com.revolut.money.transfer.db.repository.CurrencyRepository;
import com.revolut.money.transfer.exception.GenericApplicationException;
import com.revolut.money.transfer.exception.NotFoundException;
import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.Currency;

import java.math.BigDecimal;

public class AccountService {

    private AccountRepository accountRepository;
    private CurrencyRepository currencyRepository;

    public AccountService(AccountRepository accountRepository, CurrencyRepository currencyRepository) {
        this.accountRepository = accountRepository;
        this.currencyRepository = currencyRepository;
    }

    public Account findAccount(Long accountId) {
        return accountRepository.read(accountId).orElseThrow(() -> new NotFoundException("Unable to find account"));
    }

    public void deleteAccount(Long accountId) {
        accountRepository.delete(accountId);
    }

    public Account createAccount(Long userId, String currencyCode, String accountNumber) {
        Currency currency = currencyRepository.readByCurrencyCode(currencyCode.trim())
                .orElseThrow(() -> new GenericApplicationException("Unable to find currency with code " + currencyCode));

        Account account = new Account().setBalance(BigDecimal.ZERO).setUserId(userId)
                .setCurrency(currency).setAccountNumber(accountNumber);
        accountRepository.create(account);
        return account;
    }


}
