package com.wu.freamwork;

import com.wu.framework.inner.lazy.stereotype.LazyScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Jia wei Wu
 */
@EnableScheduling
@SpringBootApplication
//@LazyScan(scanBasePackages = "com.wu.freamwork.lazytable.translation.domain")
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

}
