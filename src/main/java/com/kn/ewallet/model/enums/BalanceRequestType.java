package com.kn.ewallet.model.enums;

public enum BalanceRequestType {
    ADD("add"),
    WITHDRAW("withdraw"),
    TRANSFER("transfer");

    private final String text;

    BalanceRequestType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
