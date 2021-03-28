package com.achilles.http.springboot.sentinel;

import com.MyApplicationTests;
import com.ThreadTest;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;

import com.achilles.wild.server.tool.http.HttpGetUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class FlowLimitTest extends ThreadTest {

    private final static Logger log = LoggerFactory.getLogger(FlowLimitTest.class);

    String url = "http://localhost:8080/achilles/demo/flow/";

    @Test
    public void flowTest() throws Exception{

        final List<Long> list = new ArrayList<>();
        int max = 10;
        CountDownLatch count = new CountDownLatch(max);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            final int m=i;
            executor.submit(()->{
                long threadStartTime = System.currentTimeMillis();
                String result = HttpGetUtil.get(url+m,null,getHeaderMap());
                long duration = System.currentTimeMillis() - threadStartTime;
                list.add(duration);
                log.info("-----"+m+"-----result : "+result+",duration : "+duration);
                count.countDown();
            });
        }
        count.await();
        long duration = System.currentTimeMillis() - startTime;
        log.info("---------------------------------total duration : "+duration);
        log.info("-----------------over------------each duration : "+list);
    }

    @Test
    public void flowTest2() throws Exception{
        long threadStartTime = System.currentTimeMillis();
        final int m=23;
        String result = HttpGetUtil.get(url+m,null,getHeaderMap());
        long duration = System.currentTimeMillis() - threadStartTime;
        log.info("-----"+m+"-----result : "+result+",duration : "+duration);
    }

    private Map<String, Object> getHeaderMap(){
        String traceId = GenerateUniqueUtil.getTraceId("flow");
        Map<String, Object> map = new HashMap<>();
        map.put("traceId",traceId);
        return map;
    }
}
