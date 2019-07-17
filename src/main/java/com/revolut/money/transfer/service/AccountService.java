package com.revolut.money.transfer.service;

import com.revolut.money.transfer.db.repository.AccountRepository;
import com.revolut.money.transfer.db.repository.CurrencyRepository;
import com.revolut.money.transfer.db.repository.TransactionRepository;
import com.revolut.money.transfer.db.transaction.TransactionManager;
import com.revolut.money.transfer.exception.ApplicationException;
import com.revolut.money.transfer.exception.NotFoundException;
import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.Currency;

import java.math.BigDecimal;

/**
 * Service class for account manipulation.
 */
public class AccountService {

    private AccountRepository accountRepository;
    private CurrencyRepository currencyRepository;
    private TransactionRepository transactionRepository;
    private TransactionManager transactionManager;

    public AccountService(AccountRepository accountRepository,
                          CurrencyRepository currencyRepository,
                          TransactionRepository transactionRepository,
                          TransactionManager transactionManager) {
        this.accountRepository = accountRepository;
        this.currencyRepository = currencyRepository;
        this.transactionRepository = transactionRepository;
        this.transactionManager = transactionManager;
    }

    public Account findAccount(Long accountId) {
        return accountRepository.read(accountId).orElseThrow(() -> new NotFoundException("Unable to find account"));
    }

    /**
     * Creates account with zero balance, selected currency and entered account number (for example IBAN)
     *
     * @param userId        the user for whom the account will be created
     * @param currencyCode  currency code for account
     * @param accountNumber account number
     * @return created account
     */
    public Account createAccount(Long userId, String currencyCode, String accountNumber) {
        Currency currency = currencyRepository.readByCurrencyCode(currencyCode.trim())
                .orElseThrow(() -> new ApplicationException("Unable to find currency with code " + currencyCode));

        Account account = new Account().setBalance(BigDecimal.ZERO).setUserId(userId)
                .setCurrency(currency).setAccountNumber(accountNumber);
        accountRepository.create(account);
        return account;
    }

    /**
     * Removing account with provided id. Removing account with positive balance is forbidden.
     *
     * @param accountId account to be removed
     */
    public void deleteAccount(Long accountId) {
        transactionManager.runInTransaction(() -> {
            Account account = accountRepository.readForUpdate(accountId).orElseThrow(
                    () -> new ApplicationException("Unable to find account with id " + accountId)
            );

            //check account for zero balance
            if (!account.getBalance().equals(BigDecimal.ZERO)) {
                throw new ApplicationException("Unable to remove account with positive balance");
            }

            transactionRepository.deleteAccountTransactions(accountId);
            accountRepository.delete(accountId);
        });
    }
}

