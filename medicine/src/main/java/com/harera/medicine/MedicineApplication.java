package com.harera.medicine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MedicineApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicineApplication.class, args);
	}

}
