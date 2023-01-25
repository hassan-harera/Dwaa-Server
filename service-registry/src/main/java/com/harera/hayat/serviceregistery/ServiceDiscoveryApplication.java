package com.harera.hayat.serviceregistery;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(ServiceDiscoveryApplication.class, args);
    }
}
