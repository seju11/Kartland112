package com.RaceReserve.Kartland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync 
public class KartlandApplication {

	public static void main(String[] args) {
		SpringApplication.run(KartlandApplication.class, args);

		System.out.println("Application Started....");
		
	}

}
