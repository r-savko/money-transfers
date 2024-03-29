package com.revolut.money.transfer.resource.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransferRequest {

    @NotNull
    private Long fromAccount;

    @NotNull
    private Long toAccount;

    @NotNull
    @Positive
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
