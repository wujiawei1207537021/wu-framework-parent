package com.wu.framework.easy.temple;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/9/18 下午11:30
 */
@NacosPropertySource(dataId = "temple", autoRefreshed = true)
@SpringBootApplication
@MapperScan("com.wu.framework.easy.temple.repository.mapper")
public class EasyTempleApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyTempleApplication.class);
    }
}
