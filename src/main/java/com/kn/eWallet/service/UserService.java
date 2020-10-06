package com.kn.eWallet.service;

import com.kn.eWallet.model.User;
import com.kn.eWallet.model.dto.UserDTO;
import com.kn.eWallet.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User save(final User user) {
        return userRepository.save(user);
    }

    public UserDTO getUser() {
        final Optional<User> userToFind = userRepository.findByName("Simple Test User");

        if (userToFind.isPresent()){
            final User user = userToFind.get();

            return UserDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .build();
        }

        return null;
    }
}
