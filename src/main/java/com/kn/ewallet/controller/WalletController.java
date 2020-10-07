package com.kn.ewallet.controller;

import com.kn.ewallet.model.Wallet;
import com.kn.ewallet.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(final WalletService walletService) {
        this.walletService = walletService;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> hello() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("hello world!");
    }

    @GetMapping
    public ResponseEntity<List<Wallet>> getUserWallets() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(walletService.getUserWallets(null));
    }
}