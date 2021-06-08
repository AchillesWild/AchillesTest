package com.achilles.wild.server.common.config.disruptor;

import com.achilles.wild.server.model.request.BaseRequest;
import com.lmax.disruptor.EventFactory;

public class DisruptorEventFactory implements EventFactory<BaseRequest> {

    @Override
    public BaseRequest newInstance() {
        return new BaseRequest();
    }
}
