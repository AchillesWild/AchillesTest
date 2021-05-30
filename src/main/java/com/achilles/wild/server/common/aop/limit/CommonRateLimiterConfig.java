package com.achilles.wild.server.common.aop.limit;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommonRateLimiterConfig{

    private final Map<String, RateLimiter> rateLimiterMap = new HashMap<>();


    public RateLimiter getRateLimiter(String key,Double permitsPerSecond){

        RateLimiter rateLimiter = rateLimiterMap.get(key);
        if (rateLimiter != null) {
            return rateLimiter;
        }

        synchronized (this) {
            rateLimiter = rateLimiterMap.get(key);
            if (rateLimiter != null) {
                return rateLimiter;
            }
            rateLimiter = RateLimiter.create(permitsPerSecond);
            rateLimiterMap.put(key,rateLimiter);
        }

        return rateLimiter;
    }


}
