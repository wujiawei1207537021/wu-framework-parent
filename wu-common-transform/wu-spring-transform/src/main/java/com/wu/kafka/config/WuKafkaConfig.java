package com.wu.kafka.config;

import com.supconit.its.transform.config.DataProcessConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class WuKafkaConfig {

    @Resource
    private WuDataProcessConfig wuDataProcessConfig;

    @Bean(name = "dataProcessConfig")
    public DataProcessConfig dataProcessConfig() {
        return wuDataProcessConfig;
    }


}
