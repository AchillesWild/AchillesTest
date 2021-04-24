package com.achilles.wild.server.business.controller;

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

public class CacheControllerTest extends ThreadTest {

    private final static Logger log = LoggerFactory.getLogger(CacheControllerTest.class);

    String url = urlPrefix+"cache/redisson/set/1";


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
                String result = HttpGetUtil.get(url,null,getHeaderMap("redisson"));
                long duration = System.currentTimeMillis() - threadStartTime;
                list.add(duration);
                log.info("-----"+m+"-----result : "+result+" | duration : "+duration);
                count.countDown();
            });
        }
        count.await();
        long duration = System.currentTimeMillis() - startTime;
        log.info("---------------------------------total duration : "+duration);
        log.info("-----------------over------------each duration : "+list);
    }


}
