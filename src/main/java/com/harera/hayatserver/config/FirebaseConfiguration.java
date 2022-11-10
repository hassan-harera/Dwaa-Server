package com.harera.hayatserver.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

@Configuration
public class FirebaseConfiguration {

    private final ResourceLoader resourceLoader;

    public FirebaseConfiguration(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public FirebaseAuth provideFirebaseInstance(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Bean
    public FirebaseApp initFirebase() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:hayateg.json");
        InputStream inputStream = resource.getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
