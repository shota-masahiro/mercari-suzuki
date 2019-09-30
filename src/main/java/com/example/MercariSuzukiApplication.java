package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MercariSuzukiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercariSuzukiApplication.class, args);
	}

}