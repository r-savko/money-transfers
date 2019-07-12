package com.revolut.money.transfer.service;

import com.revolut.money.transfer.db.repository.AccountRepository;
import com.revolut.money.transfer.db.repository.ExchangeRateRepository;
import com.revolut.money.transfer.db.repository.TransactionRepository;
import com.revolut.money.transfer.db.util.TransactionUtils;
import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.ExchangeRate;
import com.revolut.money.transfer.model.Transaction;
import com.revolut.money.transfer.model.TransactionStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TransferService {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private ExchangeRateRepository exchangeRateRepository;

    public TransferService(TransactionRepository transactionRepository,
                           AccountRepository accountRepository,
                           ExchangeRateRepository exchangeRateRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public Transaction transfer(Long accountIdFrom, Long accountIdTo, BigDecimal amount) {

        Transaction transaction = new Transaction().setAmount(amount)
                .setFromAccountId(accountIdFrom)
                .setToAccountId(accountIdTo)
                .setTransferDate(new Date());

        return TransactionUtils.runInTransaction(() -> {

            Account accountFrom = accountRepository.readForUpdate(accountIdFrom);
            Account accountTo = accountRepository.readForUpdate(accountIdTo);

            BigDecimal ratedAmount = applyRates(accountFrom, accountTo, amount);

            accountFrom.setBalance(accountFrom.getBalance().subtract(amount));
            accountTo.setBalance(accountTo.getBalance().add(ratedAmount));

            transaction.setStatus(TransactionStatus.COMPLETED);

            transactionRepository.create(transaction);
            return transaction;
        }).orElseGet(() -> {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        });
    }

    private BigDecimal applyRates(Account from, Account to, BigDecimal amount) {
        Long fromCurrencyId = from.getCurrency().getCurrencyId();
        Long toCurrencyId = to.getCurrency().getCurrencyId();
        if (!fromCurrencyId.equals(toCurrencyId)) {
            ExchangeRate rate = exchangeRateRepository.getExchangeRate(fromCurrencyId, toCurrencyId);
            return amount.multiply(rate.getRate());
        }
        return amount;
    }

    public List<Transaction> getAllTransactions(){
        return transactionRepository.readAll();
    }

}
