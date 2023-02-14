package com.harera.hayat.needs;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication(scanBasePackages = { "com.harera.hayat.*" })
public class NeedsApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(NeedsApplication.class, args);
    }
}
