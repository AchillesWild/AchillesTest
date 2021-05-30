package com.achilles.wild.server.common.aop.limit.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommonQpsLimit {

    double permitsPerSecond() default 1.0;

    String code() default "";

    String message() default "";
}
