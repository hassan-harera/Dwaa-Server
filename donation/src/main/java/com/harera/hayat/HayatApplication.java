package com.harera.hayat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class HayatApplication {

    public static void main(String[] args) {
        SpringApplication.run(HayatApplication.class, args);
    }
}
