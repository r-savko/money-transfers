package com.revolut.money.transfer.model;

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
}
