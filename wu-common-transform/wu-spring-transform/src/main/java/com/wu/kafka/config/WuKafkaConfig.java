package com.wu.kafka.config;

import com.supconit.its.transform.config.DataProcessConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.supconit.its.transform.config.KafkaConfig;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
