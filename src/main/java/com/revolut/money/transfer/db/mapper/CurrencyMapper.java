package com.revolut.money.transfer.db.mapper;

import com.revolut.money.transfer.model.Currency;

import java.util.Optional;

public interface CurrencyMapper extends EntityMapper<Currency> {

    Optional<Currency> readByCurrencyCode(String currencyCode);

}
