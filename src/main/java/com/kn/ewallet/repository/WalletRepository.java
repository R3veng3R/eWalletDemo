package com.kn.ewallet.repository;

import com.kn.ewallet.model.User;
import com.kn.ewallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Optional<Wallet> findById(final String uuid);
    List<Wallet> findAllByUserOrderByCreatedAtDesc(final User user);
}
