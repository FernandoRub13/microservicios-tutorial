package com.moto.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotoApplication.class, args);
	}

}
