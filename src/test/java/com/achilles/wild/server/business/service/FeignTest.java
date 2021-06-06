package com.achilles.wild.server.business.service;

import com.MyTestApplicationTests;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class FeignTest extends MyTestApplicationTests {

    private final static Logger log = LoggerFactory.getLogger(FeignTest.class);


    @Autowired
    BalanceClient balanceClient;

    @Test
    public void heartBeatTest(){
        String traceId = GenerateUniqueUtil.getTraceId("flow");
//        String result = balanceClient.heartbeat(traceId);
//        log.info("result:"+result);

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("traceId",traceId);
        String result = balanceClient.heartbeat(traceId);
        log.info("result : "+result);
    }
}
