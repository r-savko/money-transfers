package com.revolut.money.transfer.db.mapper;

import com.revolut.money.transfer.model.Transaction;

import java.util.List;

public interface TransactionMapper extends EntityMapper<Transaction> {

    List<Transaction> readAll();

}

