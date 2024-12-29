package com.example.demo.configuration;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // Carga el archivo .env
        Dotenv.configure().load();

        SpringApplication.run(Application.class, args);
    }
}
