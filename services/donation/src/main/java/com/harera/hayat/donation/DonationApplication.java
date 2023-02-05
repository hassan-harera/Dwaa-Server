package com.harera.hayat.donation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class DonationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonationApplication.class, args);
    }
}
