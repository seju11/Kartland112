package com.RaceReserve.Kartland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableAsync
public class KartlandApplication {

    private static final Logger logger = LoggerFactory.getLogger(KartlandApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KartlandApplication.class, args);
        logger.info("Kartland Application Started Successfully!");
    }
}
