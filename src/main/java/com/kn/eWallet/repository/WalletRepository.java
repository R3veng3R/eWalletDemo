package com.kn.eWallet.repository;

import com.kn.eWallet.model.User;
import com.kn.eWallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Optional<Wallet> findById(final UUID uuid);
    List<Wallet> findAllByUser(final User user);
}
