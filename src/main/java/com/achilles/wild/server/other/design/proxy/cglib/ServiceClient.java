package com.achilles.wild.server.other.design.proxy.cglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServiceClient {

    private final static Logger LOG = LoggerFactory.getLogger(ServiceClient.class);

    public void doIt(){
        LOG.info("============================================================="+this.getClass().getSimpleName());
    }
}
