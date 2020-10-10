package com.kn.ewallet.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TestUserConfig {
    @Value("${test.user.name}")
    private String name;

    @Value("${test.user.password}")
    private String password;

    @Value("${test.user.username}")
    private String username;
}
