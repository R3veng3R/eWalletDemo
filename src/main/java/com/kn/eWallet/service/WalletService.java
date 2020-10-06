package com.kn.eWallet.service;

import com.kn.eWallet.model.User;
import com.kn.eWallet.model.Wallet;
import com.kn.eWallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(final WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet saveWallet(final User user) {
        final Wallet wallet = Wallet.builder()
                .balance(0.00)
                .user(user)
                .build();

        return walletRepository.save(wallet);
    }

    public Optional<Wallet> getById(final UUID uuid) {
        return walletRepository.findById(uuid);
    }

    public boolean addBalance(final UUID uuid, double amount) {
        final Optional<Wallet> toFindWallet = walletRepository.findById(uuid);

        if (toFindWallet.isPresent()) {
            final Wallet wallet = toFindWallet.get();
            double balance = wallet.getBalance() + amount;

            wallet.setBalance(balance);
            walletRepository.save(wallet);
            return true;
        }

        return false;
    }

    public boolean withdraw(final UUID uuid, final double amount) {
        final Optional<Wallet> toFindWallet = walletRepository.findById(uuid);

        if (toFindWallet.isPresent()) {
            final Wallet wallet = toFindWallet.get();
            double balance = wallet.getBalance() - amount;

            if (balance >= 0) {
                wallet.setBalance(balance);
                walletRepository.save(wallet);
                return true;
            }
        }

        return false;
    }
}
