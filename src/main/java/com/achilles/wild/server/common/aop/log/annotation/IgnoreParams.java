package com.achilles.wild.server.common.aop.log.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreParams {

    String[] value() default {};
}
