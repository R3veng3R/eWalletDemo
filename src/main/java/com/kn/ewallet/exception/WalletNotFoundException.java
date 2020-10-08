package com.kn.ewallet.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletNotFoundException extends RuntimeException {
    private static final String WALLET_NOT_FOUND_MESSAGE = "E-Wallet was not found";
    private String message;

    public WalletNotFoundException() {
        super();
        this.message = WALLET_NOT_FOUND_MESSAGE;
    }

    public WalletNotFoundException(final String message) {
        super();
        this.message = message;
    }
}
