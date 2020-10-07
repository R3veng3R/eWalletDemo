package com.kn.ewallet.service;

import com.kn.ewallet.model.User;
import com.kn.ewallet.model.Wallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WalletServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    private User testUser;

    @BeforeEach
    public void setup() {
        this.testUser = User.builder()
                .name("Test User")
                .password("123132")
                .build();
    }

    @AfterEach
    public void tearDown() {
        this.testUser = null;
    }

    @Test
    public void saveWalletTest() {
        final User savedUser = userService.save(this.testUser);
        assertNotNull(savedUser);

        final Wallet wallet = walletService.saveWalletToUser(savedUser);

        assertNotNull(wallet);
        assertEquals(wallet.getUser(), savedUser);
    }

    @Test
    public void addBalance() {
        final User savedUser = userService.save(this.testUser);
        assertNotNull(savedUser);

        final Wallet testWallet = walletService.saveWalletToUser(savedUser);
        final BigDecimal newBalance = testWallet.getBalance().add(new BigDecimal("1000"));

        walletService.addBalance(testWallet.getId(), newBalance);

        final Optional<Wallet> wallet = walletService.getById(testWallet.getId());
        assertEquals(wallet.get().getBalance().compareTo(newBalance), 0);
    }

    @Test
    public void cannot_withdraw() {
        final User savedUser = userService.save(this.testUser);
        assertNotNull(savedUser);

        final Wallet testWallet = walletService.saveWalletToUser(savedUser);

        boolean result = walletService.withdraw(testWallet.getId(), new BigDecimal("1000"));
        assertFalse(result);
    }

    @Test
    public void can_withdraw() {
        final double amount = 1000;
        final User savedUser = userService.save(this.testUser);
        assertNotNull(savedUser);

        final Wallet testWallet = walletService.saveWalletToUser(savedUser);
        final BigDecimal newBalance = testWallet.getBalance().add(BigDecimal.valueOf(amount));
        walletService.addBalance(testWallet.getId(), newBalance);

        boolean result = walletService.withdraw(testWallet.getId(), BigDecimal.valueOf(amount));
        assertTrue(result);
    }
}