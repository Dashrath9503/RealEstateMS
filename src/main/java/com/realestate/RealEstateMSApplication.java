package com.realestate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RealEstateMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealEstateMSApplication.class, args);
        System.out.println("===========================================");
        System.out.println("  Real Estate Management System Started!  ");
        System.out.println("  URL: http://localhost:8080              ");
        System.out.println("===========================================");
    }
}
