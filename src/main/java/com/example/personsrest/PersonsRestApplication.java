package com.example.personsrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// @ComponentScan makes sure to scan and go through other specified Packages
@SpringBootApplication
@ComponentScan({"com.example.config"})
public class PersonsRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersonsRestApplication.class, args);
    }

}
