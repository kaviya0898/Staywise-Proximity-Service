package com.staywise.proximity_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ProximityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProximityServiceApplication.class, args);
	}

}
