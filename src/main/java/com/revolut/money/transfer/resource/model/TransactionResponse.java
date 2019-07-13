package com.revolut.money.transfer.resource.model;

import java.math.BigDecimal;

public class TransactionResponse {

    private Long transactionId;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
    private long transferDateInMillis;
    private String status;

    public Long getTransactionId() {
        return transactionId;
    }

    public TransactionResponse setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public TransactionResponse setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
        return this;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public TransactionResponse setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionResponse setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public long getTransferDateInMillis() {
        return transferDateInMillis;
    }

    public TransactionResponse setTransferDateInMillis(long transferDateInMillis) {
        this.transferDateInMillis = transferDateInMillis;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TransactionResponse setStatus(String status) {
        this.status = status;
        return this;
    }
}
