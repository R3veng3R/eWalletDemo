package com.kn.ewallet.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LowBalanceException extends RuntimeException {
    private static final String LOW_BALANCE_MESSAGE = "Not enough balance for this operation";
    private String message;

    public LowBalanceException() {
        this.message = LOW_BALANCE_MESSAGE;
    }

    public LowBalanceException(final String message) {
        super();
        this.message = message;
    }
}
