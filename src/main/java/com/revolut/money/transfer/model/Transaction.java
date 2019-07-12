package com.revolut.money.transfer.model;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

    private Long transactionId;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
    private Date transferDate;

    public Long getTransactionId() {
        return transactionId;
    }

    public Transaction setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public Transaction setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
        return this;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public Transaction setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public Transaction setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
        return this;
    }
};