package com.revolut.money.transfer.resource.model;

import java.math.BigDecimal;

public class TransferRequest {

    private Long fromAccount;
    private Long toAccount;
    private BigDecimal amount;

    public Long getFromAccount() {
        return fromAccount;
    }

    public TransferRequest setFromAccount(Long fromAccount) {
        this.fromAccount = fromAccount;
        return this;
    }

    public Long getToAccount() {
        return toAccount;
    }

    public TransferRequest setToAccount(Long toAccount) {
        this.toAccount = toAccount;
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
