package com.achilles.wild.server.tool.queue;

import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class ConcurrentLinkedQueueUtil {

    Queue queue = new ConcurrentLinkedQueue<>();

    @Test
    public void test() throws Exception{

        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 10000000; i++) {
            queue.offer(GenerateUniqueUtil.getUuId());
        }

        long duration = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("duration : "+duration);
        System.out.println("queue : "+queue.size());
    }
}
