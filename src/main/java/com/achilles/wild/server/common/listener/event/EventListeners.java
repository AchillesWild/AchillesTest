package com.achilles.wild.server.common.listener.event;

import com.achilles.wild.server.tool.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Component
public class EventListeners {

    private final static Logger log = LoggerFactory.getLogger(EventListeners.class);


    @EventListener
    public void addLogBizInfoEvent(MyApplicationEvent event) {

        if (event.getSource()==null){
            return;
        }

        log.info("--------EventListeners           MyApplicationEvent-----" + JsonUtil.toJsonString(event.getSource()));
    }

}
