package com.wu.smart.acw.server.config;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * describe : 基础注解配置
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/13 8:31 下午
 */
@Configuration
public class AnnotationConfig {

    private final LazyOperation lazyOperation;

    public AnnotationConfig(LazyOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }


    @PostConstruct
    public void init() {

    }


}
