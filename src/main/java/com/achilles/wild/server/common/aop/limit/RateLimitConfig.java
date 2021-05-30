package com.achilles.wild.server.common.aop.limit;

import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RateLimitConfig implements BaseRateLimitService {

    @Value("${request.rate.limit:0.2}")
    Double permitsPerSecond;

    private final Map<Double, RateLimiter> rateLimiterMap = new HashMap<>();

    @PostConstruct
    private void initRateLimiter(){

        if (permitsPerSecond == null) {
            return;
        }

        RateLimiter rateLimiter = RateLimiter.create(permitsPerSecond);

        rateLimiterMap.put(permitsPerSecond,rateLimiter);
    }

    @Override
    public RateLimiter getRateLimiter(){

        RateLimiter rateLimiter = getInstance(rateLimiterMap,permitsPerSecond);

        return rateLimiter;
    }

    @Override
    public BaseResultCode getResultCode() {
        return BaseResultCode.REQUESTS_TOO_FREQUENT;
    }
}
