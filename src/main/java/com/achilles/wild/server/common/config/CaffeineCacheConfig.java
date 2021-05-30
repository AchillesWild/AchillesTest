package com.achilles.wild.server.common.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class CaffeineCacheConfig {

    private final static Logger log = LoggerFactory.getLogger(CaffeineCacheConfig.class);

    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                .removalListener(( key,  value,  cause) ->
                        log.debug("caffeine key: " + key + "  ,value:" + value + "  ,delete reason :" + cause)
                )
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(5, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

    @Bean
    public Cache<String, AtomicInteger> caffeineCacheAtomicInteger() {
        return Caffeine.newBuilder()
                .removalListener(( key,  value,  cause) ->
                        log.debug("caffeine key: " + key + "  ,value:" + value + "  ,delete reason :" + cause)
                )
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterAccess(10, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(5)
                // 缓存的最大条数
                .maximumSize(500)
                .build();
    }
}
