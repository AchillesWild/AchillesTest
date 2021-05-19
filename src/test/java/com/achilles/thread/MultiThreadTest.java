package com.achilles.thread;

import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.google.common.base.Stopwatch;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class MultiThreadTest {

    private final static Logger log = LoggerFactory.getLogger(MultiThreadTest.class);

    public ExecutorService executor = new ThreadPoolExecutor( 100, 100, 10, TimeUnit.SECONDS,
            new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    @Test
    public void hashMapTest2() throws Exception{
        Map<String,String> map = new HashMap<>();
        String key = "achilles";
        map.put(key,"wild");
        for (int i = 0; i < 99; i++) {
            map.put(GenerateUniqueUtil.getUuId(),GenerateUniqueUtil.getUuId());
        }

        final List<Long> list = new ArrayList<>();
        int max = 1000;
        CountDownLatch count = new CountDownLatch(max);
        for (int i = 0; i < max; i++) {
            final int m=i;
            executor.submit(()->{
                Stopwatch stopwatch = Stopwatch.createStarted();
                String result = map.get(key);
                long duration = stopwatch.elapsed(TimeUnit.MICROSECONDS);
                list.add(duration);
                count.countDown();
            });
        }
        count.await();

        System.out.println(list.size());
        double avg = list.stream().mapToDouble(value -> Objects.isNull(value) ? 0L : value).average().getAsDouble();
        System.out.println("avg = " + avg);

        List<Long> sortedlist = list.stream().filter(value->value!=null).sorted().collect(Collectors.toList());
        System.out.println("sort : " + sortedlist); //Collections.sort(list);

        Collections.reverse(sortedlist);
        System.out.println("sort : " + sortedlist);
    }

    @Test
    public void hashMapTest() throws Exception{
        Map<String,String> map = new HashMap<>();
        String key = "achilles";
        map.put(key,"wild");
        for (int i = 0; i < 99; i++) {
            map.put(GenerateUniqueUtil.getUuId(),GenerateUniqueUtil.getUuId());
        }

        final List<Long> list = new ArrayList<>();
        int max = 1;
        CountDownLatch count = new CountDownLatch(max);
        for (int i = 0; i < max; i++) {
            final int m=i;
            executor.submit(()->{
                long threadStartTime = System.nanoTime();
                String result = map.get(key);
                System.out.println(System.nanoTime() - threadStartTime);
                count.countDown();
            });
        }
        count.await();
    }
}
