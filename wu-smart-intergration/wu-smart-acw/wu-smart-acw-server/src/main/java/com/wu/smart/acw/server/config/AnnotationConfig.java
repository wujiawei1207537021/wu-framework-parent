package com.wu.smart.acw.server.config;

import com.wu.framework.inner.lazy.database.smart.database.persistence.LazySmartLazyOperation;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * describe : 基础注解配置
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/13 8:31 下午
 */
@Configuration
public class AnnotationConfig {

    private final LazySmartLazyOperation lazySmartLazyOperation;

    public AnnotationConfig(LazySmartLazyOperation lazySmartLazyOperation) {
        this.lazySmartLazyOperation = lazySmartLazyOperation;
    }


//    @Async
//    @PostConstruct
    public void init() {
        ExecutorService executorService = Executors.newFixedThreadPool(1); // TODO: proper number of threads

        Future future1 = executorService.submit(() -> {
            lazySmartLazyOperation.saveUpsertSqlFile("acw");
        }); // TODO: proper type and method

        executorService.shutdown();

    }


}
