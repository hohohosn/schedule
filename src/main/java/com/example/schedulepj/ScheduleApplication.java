package com.example.schedulepj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SchedulePjApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchedulePjApplication.class, args);
    }
}