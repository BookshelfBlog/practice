package com.dev.async.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName : ThreadPoolConfig  //类名
 * @Description : 线程池配置  //描述
 */
@Configuration
public class ThreadPoolConfig {

    @Value("${Thread.MaxPoolSize}")
    private Integer MaxPoolSize;

    @Value("${Thread.CorePoolSize}")
    private Integer CorePoolSize;

    @Value("${Thread.QueueCapacity}")
    private Integer QueueCapacity;

    @Value("${Thread.KeepAliveSeconds}")
    private Integer KeepAliveSeconds;

    @Value("${Thread.ThreadNamePrefix}")
    private String ThreadNamePrefix;

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(MaxPoolSize);
        executor.setCorePoolSize(CorePoolSize);
        executor.setQueueCapacity(QueueCapacity);
        executor.setKeepAliveSeconds(KeepAliveSeconds);
        executor.setThreadNamePrefix(ThreadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }
}
