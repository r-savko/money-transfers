package com.revolut.money.transfer.db.mapper;

import com.revolut.money.transfer.model.ExchangeRate;

public interface ExchangeRateMapper extends EntityMapper<ExchangeRate, Long> {

    ExchangeRate getExchangeRate(Long currencyIdFrom, Long currencyIdTo);

}

