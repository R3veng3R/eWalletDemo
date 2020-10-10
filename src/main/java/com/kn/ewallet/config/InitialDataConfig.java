package com.kn.ewallet.config;

import com.kn.ewallet.model.User;
import com.kn.ewallet.model.Wallet;
import com.kn.ewallet.repository.UserRepository;
import com.kn.ewallet.repository.WalletRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.Instant;

@Configuration
public class InitialDataConfig {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;
    private final TestUserConfig testUserConfig;

    public InitialDataConfig(final UserRepository userRepository,
                             final WalletRepository walletRepository,
                             final PasswordEncoder passwordEncoder,
                             final TestUserConfig testUserConfig) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
        this.testUserConfig = testUserConfig;
    }

    @EventListener
    public void saveData(final ApplicationReadyEvent event) {
        User testUser = User.builder()
                .name(testUserConfig.getName())
                .password(this.passwordEncoder.encode(testUserConfig.getPassword()))
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
