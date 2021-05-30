package com.achilles.wild.server.common.listener.event;

import com.achilles.wild.server.tool.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListener implements ApplicationListener<MyApplicationEvent> {

    private final static Logger log = LoggerFactory.getLogger(MyApplicationListener.class);

    @Async
    @Override
    public void onApplicationEvent(MyApplicationEvent event) {

//        try {
//            Thread.sleep(3000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        log.info("--------MyApplicationListener    onApplicationEvent-----" + JsonUtil.toJsonString(event.getSource()));
    }
}
