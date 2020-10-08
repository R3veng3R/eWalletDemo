package com.kn.ewallet.service;

import com.kn.ewallet.exception.LowBalanceException;
import com.kn.ewallet.exception.WalletNotFoundException;
import com.kn.ewallet.model.User;
import com.kn.ewallet.model.Wallet;
import com.kn.ewallet.model.dto.BalanceRequestDTO;
import com.kn.ewallet.model.enums.BalanceRequestType;
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

    public boolean balanceRequest(final BalanceRequestDTO requestDTO) {
        if (isAddRequest(requestDTO.getType())) {
            return addBalance(requestDTO);

        } else if (isWithdrawRequest(requestDTO.getType())) {
            return withdraw(requestDTO);

        } else if (isTransferRequest(requestDTO.getType())){
            return transferWalletToWallet(requestDTO);
        }

        return false;
    }

    public boolean addBalance(final BalanceRequestDTO requestDTO) {
        final Optional<Wallet> toFindWallet = walletRepository.findById(requestDTO.getWalletId());

        if (toFindWallet.isPresent()) {
            final Wallet wallet = toFindWallet.get();
            final BigDecimal balance = wallet.getBalance().add(requestDTO.getSum());

            wallet.setBalance(balance);
            walletRepository.save(wallet);
            return true;

        } else {
            throw new WalletNotFoundException();
        }
    }

    public boolean withdraw(final BalanceRequestDTO requestDTO) throws LowBalanceException, WalletNotFoundException {
        final Optional<Wallet> toFindWallet = walletRepository.findById(requestDTO.getWalletId());

        if (toFindWallet.isPresent()) {
            final Wallet wallet = toFindWallet.get();
            final BigDecimal balance = wallet.getBalance().subtract(requestDTO.getSum());

            if (isNotNegativeBalance(balance)) {
                wallet.setBalance(balance);
                walletRepository.save(wallet);
                return true;
            } else {
                throw new LowBalanceException();
            }

        } else {
            throw new WalletNotFoundException();
        }
    }

    public boolean transferWalletToWallet(final BalanceRequestDTO requestDTO) {
        final boolean isTakenFromAccount = withdraw(requestDTO);

        if (isTakenFromAccount) {
            requestDTO.setWalletId(requestDTO.getTransferToId());
            return addBalance(requestDTO);
        }

        // System will throw exception before this boolean value is returned
        return false;
    }

    private boolean isNotNegativeBalance(final BigDecimal value) {
      return value != null && value.compareTo(BigDecimal.ZERO) > -1;
    }

    private boolean isAddRequest(final String type) {
        return BalanceRequestType.ADD.toString().equals(type);
    }

    private boolean isWithdrawRequest(final String type) {
        return BalanceRequestType.WITHDRAW.toString().equals(type);
    }

    private boolean isTransferRequest(final String type) {
        return BalanceRequestType.TRANSFER.toString().equals(type);
    }
}
