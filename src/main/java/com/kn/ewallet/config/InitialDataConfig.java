package com.kn.ewallet.config;

import com.kn.ewallet.model.User;
import com.kn.ewallet.model.Wallet;
import com.kn.ewallet.repository.UserRepository;
import com.kn.ewallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class InitialDataConfig {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${test.user.name}")
    private String testUserName;

    @Value("${test.user.password}")
    private String testUserPassword;

    public InitialDataConfig(final UserRepository userRepository,
                             final WalletRepository walletRepository,
                             final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    public void saveData(final ApplicationReadyEvent event) {
        User testUser = User.builder()
                .name(testUserName)
                .password(this.passwordEncoder.encode(testUserPassword))
                .build();

        testUser = userRepository.save(testUser);

        final Wallet wallet1 = Wallet.builder()
                .createdAt(Instant.now())
                .balance(BigDecimal.valueOf(0.00))
                .user(testUser)
                .build();

        final Wallet wallet2 = Wallet.builder()
                .createdAt(Instant.now())
                .balance(BigDecimal.valueOf(0.00))
                .user(testUser)
                .build();

        walletRepository.save(wallet1);
        walletRepository.save(wallet2);
    }
}
