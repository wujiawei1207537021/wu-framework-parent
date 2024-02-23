package com.wu.framework.database.generator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "wu.database.generator")
public class DbConfig {


    private GeneratorEnums.GeneratorTypeEnums type = GeneratorEnums.GeneratorTypeEnums.MySQL;

}
