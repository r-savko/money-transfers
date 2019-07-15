package com.revolut.money.transfer.model;

import com.google.common.base.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equal(currencyId, currency.currencyId) &&
                Objects.equal(currencyCode, currency.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(currencyId, currencyCode);
    }
}
