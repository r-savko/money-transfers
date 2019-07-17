package com.revolut.money.transfer.model;

import com.google.common.base.Objects;

import java.math.BigDecimal;

public class ExchangeRate {

    private Long exchangeRateId;
    private Long currencyFrom;
    private Long currencyTo;
    private BigDecimal rate;

    public Long getExchangeRateId() {
        return exchangeRateId;
    }

    public ExchangeRate setExchangeRateId(Long exchangeRateId) {
        this.exchangeRateId = exchangeRateId;
        return this;
    }

    public Long getCurrencyFrom() {
        return currencyFrom;
    }

    public ExchangeRate setCurrencyFrom(Long currencyFrom) {
        this.currencyFrom = currencyFrom;
        return this;
    }

    public Long getCurrencyTo() {
        return currencyTo;
    }

    public ExchangeRate setCurrencyTo(Long currencyTo) {
        this.currencyTo = currencyTo;
        return this;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public ExchangeRate setRate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate rate1 = (ExchangeRate) o;
        return Objects.equal(exchangeRateId, rate1.exchangeRateId) &&
                Objects.equal(currencyFrom, rate1.currencyFrom) &&
                Objects.equal(currencyTo, rate1.currencyTo) &&
                Objects.equal(rate, rate1.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(exchangeRateId, currencyFrom, currencyTo, rate);
    }
}
