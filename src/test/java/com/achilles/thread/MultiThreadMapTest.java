package com.achilles.thread;

import com.achilles.wild.server.other.thread.MultiThreadBase;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class MultiThreadMapTest extends MultiThreadBase{


    @Test
    public void multiThreadTest() throws Exception{
        Map<String,String> map = new HashMap<>();
        String key = "achilles";
        map.put(key,"wild");
        for (int i = 0; i < 500; i++) {
            map.put(GenerateUniqueUtil.getUuId(),GenerateUniqueUtil.getUuId());
        }

        int count = 1000;
        int maxThread = 10000;
        CountDownLatch countDownLatch = new CountDownLatch(count * maxThread);
        Stopwatch totalStopWatch = Stopwatch.createStarted();
        final List<Long> list = new Vector<>();
        for (int k = 0; k < count; k++) {
            for (int i = 0; i < maxThread; i++) {
                final int m=i;
                executor.submit(()->{
                    Stopwatch stopwatch = Stopwatch.createStarted();
                    String result = map.get(key);
                    long duration = stopwatch.elapsed(TimeUnit.MICROSECONDS);
                    list.add(duration);
                    countDownLatch.countDown();
                });
            }
        }

        countDownLatch.await();

        System.out.println("                total  duration : " + totalStopWatch.elapsed(TimeUnit.SECONDS)+"s");

        System.out.println("                requests number : " + list.size());

        double avg = list.stream().mapToDouble(value -> Objects.isNull(value) ? 0L : value).average().getAsDouble();
        System.out.println("           avg rt(microseconds) : " + avg);

        List<Long> sortedlist = list.stream().filter(value->value!=null).sorted().collect(Collectors.toList());
        Collections.reverse(sortedlist);
        System.out.println("sort rt(microseconds) max-->min : " + sortedlist.subList(0,30));
    }

}
