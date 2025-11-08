package com.RaceReserve.Kartland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync 
public class KartlandApplication {

	public static void main(String[] args) {
		SpringApplication.run(KartlandApplication.class, args);

		spring.datasource.url=jdbc:postgresql://dpg-d47l2hvdiees739f0350-a.singapore-postgres.render.com:5432/kartland_db
spring.datasource.username=kartland_db_user
spring.datasource.password=e4M3FnO4mHIcwDEqJZD853fQ7D5YI0Mv
spring.datasource.driver-class-name=org.postgresql.Driver
		
		System.out.println("Application Started....");
		
	}

}
