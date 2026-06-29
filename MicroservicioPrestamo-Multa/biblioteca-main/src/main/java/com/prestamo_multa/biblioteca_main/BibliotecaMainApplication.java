package com.prestamo_multa.biblioteca_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; // <-- Agrega este import

@SpringBootApplication
@EnableDiscoveryClient // <-- Agrega esta anotación para que se registre en Eureka
public class BibliotecaMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotecaMainApplication.class, args);
    }

}