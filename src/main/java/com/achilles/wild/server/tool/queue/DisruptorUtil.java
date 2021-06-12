package com.achilles.wild.server.tool.queue;

import com.achilles.wild.server.model.request.BaseRequest;
import com.achilles.wild.server.other.thread.MultiThreadBase;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DisruptorUtil extends MultiThreadBase {


    RingBuffer<BaseRequest> ringBuffer = messageModelRingBuffer();

    @Test
    public void multiThreadTest() throws Exception{

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
                    long sequence = ringBuffer.next();
                    BaseRequest baseRequest = ringBuffer.get(sequence);
                    baseRequest.setId("AchillesWild");
                    ringBuffer.publish(sequence);
                    long duration = stopwatch.elapsed(TimeUnit.MICROSECONDS);
                    list.add(duration);
                    countDownLatch.countDown();
                });
            }
        }

        countDownLatch.await();

        System.out.println("                total  duration : " + totalStopWatch.elapsed(TimeUnit.SECONDS)+"s");

        System.out.println("                requests number : " + ringBuffer.next());

        double avg = list.stream().mapToDouble(value -> Objects.isNull(value) ? 0L : value).average().getAsDouble();
        System.out.println("           avg rt(microseconds) : " + avg);

        List<Long> sortedlist = list.stream().filter(value->value!=null).sorted().collect(Collectors.toList());
        Collections.reverse(sortedlist);
        System.out.println("sort rt(microseconds) max-->min : " + sortedlist.subList(0,30));
    }

    @Test
    public void test() throws Exception{

        Stopwatch stopwatch = Stopwatch.createStarted();
        long sequence = 0;
        for (int i = 0; i < 10000000; i++) {
            sequence = ringBuffer.next();
            BaseRequest baseRequest = ringBuffer.get(sequence);
            baseRequest.setId(GenerateUniqueUtil.getUuId());
            ringBuffer.publish(sequence);
        }

        long duration = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("duration : "+duration);
        System.out.println("sequence : "+sequence);
    }


    private RingBuffer<BaseRequest> messageModelRingBuffer() {

        //指定事件工厂
        DisruptorEventFactory factory = new DisruptorEventFactory();

        //单线程模式，获取额外的性能
        //最低效的策略，但其对CPU的消耗最小，并且在各种部署环境中能提供更加一致的性能表现；内部维护了一个重入锁ReentrantLock和Condition
//        Disruptor<LogTimeInfo> disruptor = new Disruptor<>(
//                factory,
//                bufferSize,
//                new ThreadFactoryBuilder().setNameFormat("disruptor_consumer_%d").build(),
//                ProducerType.MULTI,
//                new BlockingWaitStrategy());

        //性能表现和com.lmax.disruptor.BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景,是一种无锁的方式
        Disruptor<BaseRequest> disruptor = new Disruptor<>(
                factory,
                1024,
                new ThreadFactoryBuilder().setNameFormat("disruptor_consumer_%d").build(),
                ProducerType.MULTI,
                new SleepingWaitStrategy());

        //性能最好，适合用于低延迟的系统；在要求极高性能且事件处理线程数小于CPU逻辑核心树的场景中，推荐使用此策略；例如，CPU开启超线程的特性；
        //也是无锁的实现，只要是无锁的实现，signalAllWhenBlocking()都是空实现；
//        Disruptor<LogTimeInfo> disruptor = new Disruptor<>(
//                factory,
//                bufferSize,
//                new ThreadFactoryBuilder().setNameFormat("disruptor_consumer_%d").build(),
//                ProducerType.MULTI,
//                new YieldingWaitStrategy());

        //设置事件业务处理器---消费者
//        disruptor.handleEventsWith(new ConsumerEventHandler());

        // 启动disruptor线程
        disruptor.start();

        RingBuffer<BaseRequest> ringBuffer = disruptor.getRingBuffer();

        return ringBuffer;
    }
}
