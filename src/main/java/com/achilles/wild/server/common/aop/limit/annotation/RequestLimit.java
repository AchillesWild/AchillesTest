package com.achilles.wild.server.common.aop.limit.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {


    int countLimit() default 100;

    double rateLimit() default 10.0;
}
