package com.pyro.cvps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReConsentProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReConsentProcessApplication.class, args);
	}


}
