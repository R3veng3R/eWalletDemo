package com.kn.ewallet.exception;

public class WalletNotFoundException extends RuntimeException {
    private String message;

    public WalletNotFoundException(final String message) {
        super();
        this.message = message;
    }
}
