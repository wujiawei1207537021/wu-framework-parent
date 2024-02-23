package com.wu.smart.acw.server;

import com.wu.framework.inner.lazy.stereotype.LazyScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@LazyScan(scanBasePackages = "com.wu.smart.acw.core.domain.uo")
@SpringBootApplication
public class AcwServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AcwServerApplication.class, args);
    }
}
