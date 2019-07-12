package com.revolut.money.transfer.model;

public enum TransactionStatus {

    COMPLETED("COMPLETED"),
    FAILED("FAILED");

    private String status;

    TransactionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
