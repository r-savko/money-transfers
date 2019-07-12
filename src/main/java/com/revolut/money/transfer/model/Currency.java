package com.revolut.money.transfer.model;

public class Currency {

    private Long currencyId;
    private String currencyCode;

    public Long getCurrencyId() {
        return currencyId;
    }

    public Currency setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Currency setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }
}
