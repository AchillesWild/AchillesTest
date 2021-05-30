package com.achilles.wild.server.other.task;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 让定时任务多线程并行执行，不阻塞（默认单线程）
 */
@Configuration
public class ScheduleConfig {

    @Bean
    public ScheduledThreadPoolExecutor scheduledExecutorService() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2,
                new ThreadFactoryBuilder().setNameFormat("schedule_pool_%d").build());
        return executor;
    }
}
