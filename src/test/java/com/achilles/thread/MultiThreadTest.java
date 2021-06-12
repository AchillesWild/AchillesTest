package com.achilles.thread;

import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class MultiThreadTest {


    public ExecutorService executor = new ThreadPoolExecutor( 1500, 2000, 10, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100000000), new ThreadPoolExecutor.AbortPolicy());

    @Test
    public void hashMapTest2() throws Exception{
        Map<String,String> map = new HashMap<>();
        String key = "achilles";
        map.put(key,"wild");
        for (int i = 0; i < 500; i++) {
            map.put(GenerateUniqueUtil.getUuId(),GenerateUniqueUtil.getUuId());
        }

        int count = 1000;
        int maxThread = 10000;
        CountDownLatch countDownLatch = new CountDownLatch(count * maxThread);
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

        System.out.println("size : " + list.size());

        double avg = list.stream().mapToDouble(value -> Objects.isNull(value) ? 0L : value).average().getAsDouble();
        System.out.println("avg : " + avg);

//        List<Long> sortedlist = list.stream().filter(value->value!=null).sorted().collect(Collectors.toList());
//        System.out.println("sort : " + sortedlist.subList(0,20)); //Collections.sort(list);

        List<Long> sortedlist = list.stream().filter(value->value!=null).sorted().collect(Collectors.toList());
        Collections.reverse(sortedlist);
        System.out.println("sort : " + sortedlist.subList(0,30));
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
