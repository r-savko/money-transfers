package com.revolut.money.transfer.resource.model;

import java.math.BigDecimal;

public class AccountResponse {

    private Long accountId;
    private Long userId;
    private String accountNumber;
    private BigDecimal balance;
    private String currency;

    public Long getAccountId() {
        return accountId;
    }

    public AccountResponse setAccountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public AccountResponse setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountResponse setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountResponse setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public AccountResponse setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
}
