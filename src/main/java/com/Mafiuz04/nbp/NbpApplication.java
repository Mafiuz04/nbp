package com.Mafiuz04.nbp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class NbpApplication {

	public static void main(String[] args) {
		SpringApplication.run(NbpApplication.class, args);
	}

}
