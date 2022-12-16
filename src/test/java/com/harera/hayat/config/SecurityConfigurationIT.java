package com.harera.hayat.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.harera.hayat.ApplicationIT;
import com.harera.hayat.stub.PasswordStubs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class SecurityConfigurationIT extends ApplicationIT {

    private final PasswordStubs passwordStubs;

    @Test
    void printEncodedPassword() {
        System.out.println(passwordStubs.encode("harere"));
    }
}
