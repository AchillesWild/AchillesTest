package com.achilles.wild.server.tool.queue;

import com.achilles.wild.server.other.thread.MultiThreadBase;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class LinkedBlockingQueueUtil  extends MultiThreadBase {

    Queue queue = new LinkedBlockingQueue<>(100000000);

    @Test
    public void multiThreadTest() throws Exception{

        int count = 1000;
        int maxThread = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(count * maxThread);
        Stopwatch totalStopWatch = Stopwatch.createStarted();
        final List<Long> list = new Vector<>();
        for (int k = 0; k < count; k++) {
            for (int i = 0; i < maxThread; i++) {
                final int m=i;
                executor.submit(()->{
                    Stopwatch stopwatch = Stopwatch.createStarted();
                    queue.offer("AchillesWild");
                    long duration = stopwatch.elapsed(TimeUnit.MICROSECONDS);
                    list.add(duration);
                    countDownLatch.countDown();
                });
            }
        }

        countDownLatch.await();

        System.out.println("                total  duration : " + totalStopWatch.elapsed(TimeUnit.SECONDS)+"s");

        System.out.println("                requests number : " + queue.size());

        double avg = list.stream().mapToDouble(value -> Objects.isNull(value) ? 0L : value).average().getAsDouble();
        System.out.println("           avg rt(microseconds) : " + avg);

        List<Long> sortedlist = list.stream().filter(value->value!=null).sorted().collect(Collectors.toList());
        Collections.reverse(sortedlist);
        System.out.println("sort rt(microseconds) max-->min : " + sortedlist.subList(0,30));
    }

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
