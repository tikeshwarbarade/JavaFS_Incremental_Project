package com.edutech.progressive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EduConnectApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduConnectApplication.class, args);
        System.out.println("Welcome to EduConnect Progressive Project!");
    }
}
