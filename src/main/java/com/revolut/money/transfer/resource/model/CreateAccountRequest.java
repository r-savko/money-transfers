package com.revolut.money.transfer.resource.model;

public class CreateAccountRequest {

    private String currencyCode;

    private String accountNumber;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public CreateAccountRequest setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public CreateAccountRequest setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }
}
