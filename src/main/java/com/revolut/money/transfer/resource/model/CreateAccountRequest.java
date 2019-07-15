package com.revolut.money.transfer.resource.model;

import javax.validation.constraints.NotEmpty;

public class CreateAccountRequest {

    @NotEmpty
    private String currencyCode;

    @NotEmpty
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
