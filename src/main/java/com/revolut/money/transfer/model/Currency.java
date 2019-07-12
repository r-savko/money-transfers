package com.revolut.money.transfer.model;

public class Currency {

    private Long currencyId;
    private String name;

    public Long getCurrencyId() {
        return currencyId;
    }

    public Currency setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Currency setName(String name) {
        this.name = name;
        return this;
    }
}
