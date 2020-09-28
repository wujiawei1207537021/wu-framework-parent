package com.wu.framework.easy.temple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/9/18 下午11:30
 */
@EnableSwagger2
@SpringBootApplication
public class EasyTempleApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyTempleApplication.class);
    }
}
