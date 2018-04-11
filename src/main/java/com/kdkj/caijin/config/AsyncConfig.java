package com.kdkj.caijin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 异步任务配置
 *
 * @author lin
 * @create 2018-04-10 17:13
 **/
@Configuration
@EnableAsync
public class AsyncConfig {
    @Value("${thread.core-pool-size}")
    private int corePoolSize = 10;
    @Value("${thread.max-pool-size}")
    private int maxPoolSize = 200;
    @Value("${thread.queue-capacity}")
    private int queueCapacity = 10;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }
}
