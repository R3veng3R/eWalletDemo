package com.kn.ewallet.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LowBalanceException extends RuntimeException {
    private String message;

    public LowBalanceException(final String message) {
        super();
        this.message = message;
    }
}
