package com.kn.eWallet.service;

import com.kn.eWallet.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setup() {
        this.testUser = User.builder()
                .name("Test User")
                .password("123132")
                .build();
    }

    @Test
    public void saveUserTest() {
        final User savedUser = userService.save(this.testUser);
        assertNotNull(savedUser);
    }

}