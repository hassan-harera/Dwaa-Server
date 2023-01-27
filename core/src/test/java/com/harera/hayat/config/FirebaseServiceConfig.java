package com.harera.hayat.core.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.harera.hayat.core.service.firebase.FirebaseService;

@Configuration
public class FirebaseServiceConfig {

    @Bean
    @Primary
    public FirebaseService firebaseService() {
        return Mockito.mock(FirebaseService.class);
    }
}
