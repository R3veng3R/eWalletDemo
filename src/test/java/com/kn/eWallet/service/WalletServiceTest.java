package com.kn.eWallet.service;

import com.kn.eWallet.model.User;
import com.kn.eWallet.model.Wallet;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        final Wallet wallet = walletService.saveWallet(savedUser);

        assertNotNull(wallet);
        assertEquals(wallet.getUser(), savedUser);
    }

    @Test
    public void addBalance() {
        final User savedUser = userService.save(this.testUser);
        assertNotNull(savedUser);

        final Wallet testWallet = walletService.saveWallet(savedUser);
        double newBalance = testWallet.getBalance() + 1000;

        walletService.addBalance(testWallet.getId(), newBalance);

        final Optional<Wallet> wallet = walletService.getById(testWallet.getId());
        assertEquals(newBalance, wallet.get().getBalance());
    }

    @Test
    public void cannot_withdraw() {
        final User savedUser = userService.save(this.testUser);
        assertNotNull(savedUser);

        final Wallet testWallet = walletService.saveWallet(savedUser);

        boolean result = walletService.withdraw(testWallet.getId(), 1000);
        assertFalse(result);
    }

    @Test
    public void can_withdraw() {
        final double amount = 1000;
        final User savedUser = userService.save(this.testUser);
        assertNotNull(savedUser);

        final Wallet testWallet = walletService.saveWallet(savedUser);
        double newBalance = testWallet.getBalance() + amount;
        walletService.addBalance(testWallet.getId(), newBalance);

        boolean result = walletService.withdraw(testWallet.getId(), amount);
        assertTrue(result);
    }
}