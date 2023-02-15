package com.harera.hayat.donation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableMongoRepositories
@EnableEurekaClient
@EnableWebMvc
@SpringBootApplication(scanBasePackages = { "com.harera.hayat.*" })
public class DonationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonationApplication.class, args);
    }
}
