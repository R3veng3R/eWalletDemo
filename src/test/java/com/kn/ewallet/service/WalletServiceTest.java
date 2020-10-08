package com.kn.ewallet.service;

import com.kn.ewallet.exception.LowBalanceException;
import com.kn.ewallet.model.User;
import com.kn.ewallet.model.Wallet;
import com.kn.ewallet.model.dto.BalanceRequestDTO;
import com.kn.ewallet.model.enums.BalanceRequestType;
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

        final BalanceRequestDTO dto = BalanceRequestDTO.builder()
                .sum(newBalance)
                .walletId(testWallet.getId())
                .type(BalanceRequestType.ADD.toString())
                .build();

        walletService.addBalance(dto);

        final Optional<Wallet> wallet = walletService.getById(testWallet.getId());
        assertEquals(wallet.get().getBalance().compareTo(newBalance), 0);
    }

    @Test
    public void cannot_withdraw() {
        assertThrows(LowBalanceException.class, () -> {
            final User savedUser = userService.save(this.testUser);
            assertNotNull(savedUser);

            final Wallet testWallet = walletService.saveWalletToUser(savedUser);

            final BalanceRequestDTO dto = BalanceRequestDTO.builder()
                    .sum(new BigDecimal("1000"))
                    .walletId(testWallet.getId())
                    .type(BalanceRequestType.WITHDRAW.toString())
                    .build();

            walletService.withdraw(dto);
        });
    }

    @Test
    public void can_withdraw() {
        final double amount = 1000;
        final User savedUser = userService.save(this.testUser);
        assertNotNull(savedUser);

        final Wallet testWallet = walletService.saveWalletToUser(savedUser);
        final BigDecimal newBalance = testWallet.getBalance().add(BigDecimal.valueOf(amount));

        final BalanceRequestDTO dto = BalanceRequestDTO.builder()
                .sum(newBalance)
                .walletId(testWallet.getId())
                .type(BalanceRequestType.ADD.toString())
                .build();

        walletService.addBalance(dto);

        dto.setType(BalanceRequestType.WITHDRAW.toString());
        boolean result = walletService.withdraw(dto);
        assertTrue(result);
    }

    @Test
    public void can_transfer_to_another_account() {
        final BigDecimal sumToTransfer = BigDecimal.valueOf(1000);
        final User savedUser = userService.save(this.testUser);
        assertNotNull(savedUser);

        final Wallet fromWallet = walletService.saveWalletToUser(savedUser);
        final Wallet transferToWallet = walletService.saveWalletToUser(savedUser);

        final BalanceRequestDTO addBalanceDto = BalanceRequestDTO.builder()
                .sum(sumToTransfer)
                .walletId(fromWallet.getId())
                .type(BalanceRequestType.ADD.toString())
                .build();

        final boolean isAddedBalance = walletService.addBalance(addBalanceDto);
        assertTrue(isAddedBalance);

        final BalanceRequestDTO transferBalanceDTO = BalanceRequestDTO.builder()
                .sum(sumToTransfer)
                .walletId(fromWallet.getId())
                .transferToId(transferToWallet.getId())
                .type(BalanceRequestType.TRANSFER.toString())
                .build();

        walletService.balanceRequest(transferBalanceDTO);

        final Wallet transferredBalanceWallet = walletService.getById(transferToWallet.getId()).get();
        assertEquals(transferredBalanceWallet.getBalance().compareTo(sumToTransfer), 0);

        final Wallet withdrawnBalanceWallet = walletService.getById(fromWallet.getId()).get();
        assertEquals(withdrawnBalanceWallet.getBalance().compareTo(sumToTransfer), -1);
    }

    @Test
    public void can_not_transfer_to_another_account() {
        assertThrows(LowBalanceException.class, () -> {
            final BigDecimal sumToTransfer = BigDecimal.valueOf(1000);
            final User savedUser = userService.save(this.testUser);
            assertNotNull(savedUser);

            final Wallet fromWallet = walletService.saveWalletToUser(savedUser);
            final Wallet transferToWallet = walletService.saveWalletToUser(savedUser);

            final BalanceRequestDTO transferBalanceDTO = BalanceRequestDTO.builder()
                    .sum(sumToTransfer)
                    .walletId(fromWallet.getId())
                    .transferToId(transferToWallet.getId())
                    .type(BalanceRequestType.TRANSFER.toString())
                    .build();

            walletService.balanceRequest(transferBalanceDTO);
        });
    }
}