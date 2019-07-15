package com.revolut.money.transfer.model;

import com.google.common.base.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equal(transactionId, that.transactionId) &&
                Objects.equal(account, that.account) &&
                Objects.equal(amount, that.amount) &&
                Objects.equal(transferDate, that.transferDate) &&
                type == that.type &&
                Objects.equal(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(transactionId, account, amount, transferDate, type, message);
    }
}