package com.kn.ewallet.controller;

import com.kn.ewallet.model.Wallet;
import com.kn.ewallet.model.dto.BalanceRequestDTO;
import com.kn.ewallet.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(final WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public List<Wallet> getUserWallets() {
        return walletService.getUserWallets();
    }

    @PostMapping
    public Wallet createWallet() {
        return walletService.createWallet();
    }

    @PutMapping
    public boolean balanceRequest(@RequestBody final BalanceRequestDTO balanceRequestDTO) {
        return walletService.balanceRequest(balanceRequestDTO);
    }
}
