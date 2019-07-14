package com.revolut.money.transfer.model;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

    private Long transactionId;
    private Long account;
    private BigDecimal amount;
    private Date transferDate;
    private TransactionType type;
    private String message;

    public Long getTransactionId() {
        return transactionId;
    }

    public Transaction setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public Long getAccount() {
        return account;
    }

    public Transaction setAccount(Long account) {
        this.account = account;
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

    public TransactionType getType() {
        return type;
    }

    public Transaction setType(TransactionType type) {
        this.type = type;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Transaction setMessage(String message) {
        this.message = message;
        return this;
    }
}