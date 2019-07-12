package com.revolut.money.transfer.resource.model;

import java.math.BigDecimal;

public class TransferRequest {

    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public TransferRequest setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
        return this;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public TransferRequest setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransferRequest setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}
