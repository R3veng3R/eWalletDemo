package com.kn.eWallet.util;

import com.kn.eWallet.model.User;
import com.kn.eWallet.model.Wallet;
import com.kn.eWallet.repository.UserRepository;
import com.kn.eWallet.repository.WalletRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialData {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;

    public InitialData(final UserRepository userRepository,
                       final WalletRepository walletRepository,
                       final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    public void saveData(final ApplicationReadyEvent event) {
        User testUser = User.builder()
                .name("Simple Test User")
                .password(this.passwordEncoder.encode("123123"))
                .build();

        testUser = userRepository.save(testUser);

        final Wallet wallet1 = Wallet.builder()
                .user(testUser)
                .build();

        final Wallet wallet2 = Wallet.builder()
                .user(testUser)
                .build();

        walletRepository.save(wallet1);
        walletRepository.save(wallet2);
    }
}
