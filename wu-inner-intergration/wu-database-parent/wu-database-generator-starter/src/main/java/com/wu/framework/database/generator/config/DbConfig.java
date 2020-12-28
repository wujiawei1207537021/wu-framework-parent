package com.wu.framework.database.generator.config;

import com.wu.framework.database.generator.repository.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Data
@Configuration
@ConfigurationProperties(prefix = "wu.database.generator")
public class DbConfig {


    private GeneratorEnums.GeneratorTypeEnums type = GeneratorEnums.GeneratorTypeEnums.MySQL;

}
