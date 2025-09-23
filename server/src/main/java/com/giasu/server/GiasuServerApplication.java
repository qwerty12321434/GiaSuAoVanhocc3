package com.giasu.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.giasu.server")
@EnableAutoConfiguration
public class GiasuServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GiasuServerApplication.class, args);
    }
}

