package com.revolut.money.transfer.model;

import com.google.common.base.Objects;

import java.math.BigDecimal;

public class Account {

    private Long accountId;
    private Long userId;
    private String accountNumber;
    private BigDecimal balance;
    private Currency currency;

    public Long getAccountId() {
        return accountId;
    }

    public Account setAccountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Account setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Account setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Account setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Account setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void debit(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equal(accountId, account.accountId) &&
                Objects.equal(userId, account.userId) &&
                Objects.equal(accountNumber, account.accountNumber) &&
                Objects.equal(balance, account.balance) &&
                Objects.equal(currency, account.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(accountId, userId, accountNumber, balance, currency);
    }
}
