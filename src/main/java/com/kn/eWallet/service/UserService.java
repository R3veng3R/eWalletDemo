package com.kn.eWallet.service;

import com.kn.eWallet.model.User;
import com.kn.eWallet.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(final User user) {
        return userRepository.save(user);
    }
}
