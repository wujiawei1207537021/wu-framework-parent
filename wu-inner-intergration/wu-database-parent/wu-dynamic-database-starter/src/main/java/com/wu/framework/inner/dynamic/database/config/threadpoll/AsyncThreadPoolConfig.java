package com.wu.framework.inner.dynamic.database.config.threadpoll;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


@Slf4j
@EnableAsync
@Configuration
public class AsyncThreadPoolConfig {

    private static final Logger logger = LoggerFactory.getLogger(AsyncThreadPoolConfig.class);

    @Bean
    public Executor asyncServiceExecutor() {
        log.info("start asyncServiceExecutor");
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //      ThreadPoolTaskExecutor executor = new VisibleThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(5);
        // 设置最大线程数
        executor.setMaxPoolSize(10);
        // 设置队列容量
        executor.setQueueCapacity(1000);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("async-service-");
        //拒绝开启新线程策略,不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }


}
