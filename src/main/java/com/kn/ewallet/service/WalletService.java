package com.kn.ewallet.service;

import com.kn.ewallet.model.User;
import com.kn.ewallet.model.Wallet;
import com.kn.ewallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class WalletService {
    private final WalletRepository walletRepository;
    private final UserService userService;

    public WalletService(final WalletRepository walletRepository, final UserService userService) {
        this.walletRepository = walletRepository;
        this.userService = userService;
    }

    public List<Wallet> getUserWallets() {
        final User user = userService.getAuthenticatedUser();
        return walletRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    public Wallet saveWalletToUser(final User user) {
        final Wallet wallet = Wallet.builder()
                .createdAt(Instant.now())
                .balance(BigDecimal.valueOf(0.00))
                .user(user)
                .build();

        return walletRepository.save(wallet);
    }

    public Wallet createWallet() {
        return walletRepository.save(
                Wallet.builder()
                        .createdAt(Instant.now())
                        .balance(BigDecimal.valueOf(0.00))
                        .user(userService.getAuthenticatedUser())
                        .build()
        );
    }

    public Optional<Wallet> getById(final UUID uuid) {
        return walletRepository.findById(uuid);
    }

    public boolean addBalance(final UUID uuid, final BigDecimal amount) {
        final Optional<Wallet> toFindWallet = walletRepository.findById(uuid);

        if (toFindWallet.isPresent()) {
            final Wallet wallet = toFindWallet.get();
            final BigDecimal balance = wallet.getBalance().add(amount);

            wallet.setBalance(balance);
            walletRepository.save(wallet);
            return true;
        }

        return false;
    }

    public boolean withdraw(final UUID uuid, final BigDecimal amount) {
        final Optional<Wallet> toFindWallet = walletRepository.findById(uuid);

        if (toFindWallet.isPresent()) {
            final Wallet wallet = toFindWallet.get();
            final BigDecimal balance = wallet.getBalance().subtract(amount);

            if (isNotNegativeBalance(balance)) {
                wallet.setBalance(balance);
                walletRepository.save(wallet);
                return true;
            }
        }

        return false;
    }

    private boolean isNotNegativeBalance(final BigDecimal value) {
        return value.compareTo(new BigDecimal("0.00")) > -1;
    }
}
