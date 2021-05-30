package com.achilles.wild.server.common.aop.limit;

import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.google.common.util.concurrent.RateLimiter;

import java.util.Map;

public interface BaseRateLimitService {

    RateLimiter getRateLimiter();

    BaseResultCode getResultCode();

    default BaseResultCode getDefaultResultCode (){
        return BaseResultCode.REQUESTS_TOO_FREQUENT;
    }

    default RateLimiter getInstance(Map<Double, RateLimiter> rateLimiterMap, Double permitsPerSecond){

        if (rateLimiterMap == null) {
            throw new IllegalArgumentException("rateLimiterMap can not be null !");
        }
        if (permitsPerSecond == null || permitsPerSecond <= 0) {
            throw new IllegalArgumentException("permitsPerSecond can not be null or less than 0 !");
        }

        RateLimiter rateLimiter = rateLimiterMap.get(permitsPerSecond);
        if (rateLimiter != null) {
            return rateLimiter;
        }

        synchronized (rateLimiterMap) {
            rateLimiter = rateLimiterMap.get(permitsPerSecond);
            if (rateLimiter == null) {
                rateLimiter = RateLimiter.create(permitsPerSecond);
                rateLimiterMap.put(permitsPerSecond,rateLimiter);
            }
        }

        return rateLimiter;
    }
}
