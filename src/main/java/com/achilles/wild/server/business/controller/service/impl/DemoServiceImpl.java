package com.achilles.wild.server.business.controller.service.impl;

import com.achilles.wild.server.business.controller.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Async
    @Override
    public void doIt(String name) {
        log.info("doIt name : " + name );
    }

    @Async
    @Override
    public Future<String> asyncReturnFuture(String name) {

        log.info("asyncReturnFuture name : " + name );

        Future<String> future = null;
        try {
            Thread.sleep(2000L);
            future = new AsyncResult<>("Wild_" + name);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return future;
    }

}
