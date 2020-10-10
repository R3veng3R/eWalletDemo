package com.kn.ewallet.service;

import com.kn.ewallet.config.TestUserConfig;
import com.kn.ewallet.model.User;
import com.kn.ewallet.model.dto.UserDTO;
import com.kn.ewallet.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TestUserConfig testUserConfig;

    public UserService(final UserRepository userRepository,
                       final TestUserConfig testUserConfig) {
        this.userRepository = userRepository;
        this.testUserConfig = testUserConfig;
    }

    @Transactional
    public User save(final User user) {
        return userRepository.save(user);
    }

    public UserDTO getUser() {
        final Optional<User> userToFind = userRepository.findByName(testUserConfig.getName());

        if (userToFind.isPresent()){
            final User user = userToFind.get();

            return UserDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .build();
        }

        return null;
    }

    public User getAuthenticatedUser() {
        return userRepository.findByName(testUserConfig.getName()).get();
    }
}
