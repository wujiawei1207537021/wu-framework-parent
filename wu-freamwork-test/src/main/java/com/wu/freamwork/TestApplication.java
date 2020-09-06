package com.wu.freamwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableScheduling
@SpringBootApplication
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class,args);
    }
}
