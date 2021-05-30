package com.achilles.wild.server.common.aop.limit.annotation;

import com.achilles.wild.server.common.aop.limit.BaseRateLimitService;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {


    Class<?> limitClass() default BaseRateLimitService.class;
}
