package com.achilles.wild.server.common.aop.limit.sentinel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FallBackHandler {

    private final static Logger log = LoggerFactory.getLogger(FallBackHandler.class);


    public static String fallback(String name, Throwable e) {
        log.info("==================fallback=========="+name);
        return "fallback";
    }
}
