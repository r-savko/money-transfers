package com.revolut.money.transfer.db.mapper;

import com.revolut.money.transfer.model.ExchangeRate;

import java.util.Optional;

public interface ExchangeRateMapper extends EntityMapper<ExchangeRate> {

    Optional<ExchangeRate> readExchangeRate(Long currencyIdFrom, Long currencyIdTo);

}

