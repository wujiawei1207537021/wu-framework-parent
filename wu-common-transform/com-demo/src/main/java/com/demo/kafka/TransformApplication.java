package com.demo.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class TransformApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransformApplication.class, args);
    }

}
