package com.achilles.wild.server.business.controller.demo;


import com.achilles.wild.server.common.aop.exception.BizException;
import com.achilles.wild.server.model.request.BaseRequest;
import com.achilles.wild.server.model.response.BaseResult;
import com.achilles.wild.server.model.response.code.BaseResultCode;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.lmax.disruptor.RingBuffer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
@RequestMapping(value = "/queue", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
public class QueueController {

    int capacity = 10000000;

    private final Queue arrayBlockingQueue = new ArrayBlockingQueue<>(capacity);

    private final Queue linkedBlockingQueue = new LinkedBlockingQueue<>(capacity);

    private final Queue concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    @Autowired
    RingBuffer<BaseRequest> ringBuffer;

    @PostMapping(path = "/ConcurrentLinkedQueue/add")
    public Object concurrentLinkedQueue(){

//        log.info("-----------------ConcurrentLinkedQueue-----------------");

        boolean result  = concurrentLinkedQueue.offer(GenerateUniqueUtil.getUuId());
        if (!result) {
            throw new BizException(BaseResultCode.FAIL);
        }

        return new BaseResult();
    }

    @PostMapping(path = "/LinkedBlockingQueue/add")
    public Object linkedBlockingQueue(){

//        log.info("-----------------linkedBlockingQueue-----------------");

        boolean result  = linkedBlockingQueue.offer(GenerateUniqueUtil.getUuId());
        if (!result) {
            throw new BizException(BaseResultCode.FAIL);
        }

        return new BaseResult();
    }

    @PostMapping(path = "/ArrayBlockingQueue/add")
    public Object arrayBlockingQueue(){

//        log.info("-----------------arrayBlockingQueue-----------------");

        boolean result  = arrayBlockingQueue.offer(GenerateUniqueUtil.getUuId());
        if (!result) {
            throw new BizException(BaseResultCode.FAIL);
        }

        return new BaseResult();
    }

    @PostMapping(path = "/Disruptor/add")
    public Object disruptor(){

//        log.info("-----------------disruptor-----------------");

        long sequence = ringBuffer.next();
        BaseRequest baseRequest = ringBuffer.get(sequence);
        baseRequest.setId(GenerateUniqueUtil.getUuId());
        ringBuffer.publish(sequence);

        return new BaseResult();
    }
}
