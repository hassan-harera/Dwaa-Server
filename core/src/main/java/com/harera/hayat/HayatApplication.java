package com.harera.hayat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "com.harera.hayat.*")
public class HayatApplication {

    public static void main(String[] args) {
        SpringApplication.run(HayatApplication.class, args);
    }
}
