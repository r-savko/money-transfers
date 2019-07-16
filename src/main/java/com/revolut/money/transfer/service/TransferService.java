package com.revolut.money.transfer.service;

import com.revolut.money.transfer.db.repository.AccountRepository;
import com.revolut.money.transfer.db.repository.ExchangeRateRepository;
import com.revolut.money.transfer.db.repository.TransactionRepository;
import com.revolut.money.transfer.db.transaction.TransactionManager;
import com.revolut.money.transfer.exception.ApplicationException;
import com.revolut.money.transfer.model.Account;
import com.revolut.money.transfer.model.ExchangeRate;
import com.revolut.money.transfer.model.Transaction;
import com.revolut.money.transfer.model.TransactionType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Service for transferring funds between two accounts
 */
public class TransferService {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private ExchangeRateRepository exchangeRateRepository;
    private TransactionManager transactionManager;

    public TransferService(TransactionRepository transactionRepository,
                           AccountRepository accountRepository,
                           ExchangeRateRepository exchangeRateRepository,
                           TransactionManager transactionManager) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.transactionManager = transactionManager;
    }

    /**
     * Transfers funds between two accounts
     *
     * @param accountIdFrom source account identifier for transfer
     * @param accountIdTo destination account identifier for transfer
     * @param amount transfer amount
     * @return transaction info
     */
    public Transaction transfer(Long accountIdFrom, Long accountIdTo, BigDecimal amount) {

        //create draft of debit transaction
        Transaction debitTransaction = new Transaction().setAmount(amount).setAccount(accountIdFrom)
                .setTransferDate(new Date()).setType(TransactionType.DEBIT)
                .setMessage("Transfer to account with id " + accountIdTo);

        //create draft of credit transaction
        Transaction creditTransaction = new Transaction().setAmount(amount).setAccount(accountIdTo)
                .setTransferDate(new Date()).setType(TransactionType.CREDIT)
                .setMessage("Transfer from account with id " + accountIdFrom);

        //open transaction
        return transactionManager.runInTransaction(() -> {

            //pessimistic locking for account records
            Account accountFrom = accountRepository.readForUpdate(accountIdFrom).orElseThrow(
                    () -> new ApplicationException("Unable to found account with id " + accountIdFrom)
            );
            Account accountTo = accountRepository.readForUpdate(accountIdTo).orElseThrow(
                    () -> new ApplicationException("Unable to found account with id " + accountIdTo)
            );

            //validating transfer amount
            validateTransferAmount(accountFrom, amount);

            //apply currency conversion rates
            BigDecimal ratedAmount = applyRates(accountFrom, accountTo, amount);

            //update accounts balance
            accountFrom.debit(amount);
            accountTo.credit(ratedAmount);

            //update transaction info with transfer amounts
            debitTransaction.setAmount(amount);
            creditTransaction.setAmount(ratedAmount);

            //update accounts in database
            accountRepository.update(accountFrom);
            accountRepository.update(accountTo);

            //save transaction info
            transactionRepository.create(debitTransaction);
            transactionRepository.create(creditTransaction);

            return debitTransaction;
        });
    }

    /**
     * Apply currency exchange rates
     * @param from source account
     * @param to destination account
     * @param amount amount of funds for transfer
     * @return adjusted transfer amount based on currency exchange rate
     */
    private BigDecimal applyRates(Account from, Account to, BigDecimal amount) {
        Long fromCurrencyId = from.getCurrency().getCurrencyId();
        Long toCurrencyId = to.getCurrency().getCurrencyId();
        if (!fromCurrencyId.equals(toCurrencyId)) {
            ExchangeRate rate = exchangeRateRepository.readExchangeRate(fromCurrencyId, toCurrencyId)
                    .orElseThrow(() -> new ApplicationException("Unable to find exchange rate"));
            return amount.multiply(rate.getRate());
        }
        return amount;
    }

    /**
     * Transfer amount validation. Account balance should be greater or equal of transfer amount.
     * @param account account to be validated
     * @param amount transfer amount to be validated
     */
    private void validateTransferAmount(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new ApplicationException("Transaction amount is greater than account balance");
        }
    }

    /**
     * Get all transactions associated with account
     * @param accountId account identifier
     * @return list with all transactions for account
     */
    public List<Transaction> findAccountTransactions(Long accountId) {
        return transactionRepository.readAccountTransactions(accountId);
    }
}
