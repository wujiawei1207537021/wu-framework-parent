package com.wu.framework.inner.lazy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2021/2/4 下午6:48
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.application")
public class LazyApplicationConfig {

    private String name;
}
