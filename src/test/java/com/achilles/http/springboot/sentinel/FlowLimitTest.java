package com.achilles.http.springboot.sentinel;

import com.MyApplicationTests;
import com.achilles.tool.http.HttpGetUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class FlowLimitTest extends MyApplicationTests {

    private final static Logger log = LoggerFactory.getLogger(FlowLimitTest.class);

    String url = "http://localhost:8080/achilles/demo/flow/";

    @Test
    public void flowTest() throws Exception{

        final List<Long> list = new ArrayList<>();
        int max = 1;
        CountDownLatch count = new CountDownLatch(max);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            final int m=i;
            new Thread(()->{
                long threadStartTime = System.currentTimeMillis();
                String result = HttpGetUtil.get(url+m,null);
                long duration = System.currentTimeMillis() - threadStartTime;
                list.add(duration);
                log.info("-----"+m+"-----result : "+result+",duration : "+duration);
                count.countDown();
            }).start();
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
        String result = HttpGetUtil.get(url+m,null);
        long duration = System.currentTimeMillis() - threadStartTime;
        log.info("-----"+m+"-----result : "+result+",duration : "+duration);
    }
}
