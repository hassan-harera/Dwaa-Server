package com.harera.hayat.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.harera.hayat.ApplicationIT;
import com.harera.hayat.stub.PasswordStubs;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class SecurityConfigurationIT extends ApplicationIT {

    @Autowired
    private PasswordStubs passwordStubs;

    @Test
    void printEncodedPassword() {
        System.out.println(passwordStubs.encode("password"));
    }
}
