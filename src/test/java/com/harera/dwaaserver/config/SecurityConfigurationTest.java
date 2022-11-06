package com.harera.dwaaserver.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
class SecurityConfigurationTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void encodePassword() {
        System.out.println(passwordEncoder.encode("harerastar"));
    }

}