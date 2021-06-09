package com.achilles.wild.server.common.config.disruptor;

import com.achilles.wild.server.model.request.BaseRequest;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DisruptorConfig {

    //2^20 1048576  2^14 16384
    @Value("${disruptor.ring.buffer.size:2}")//2^13指定 RingBuffer 大小，必须为2的N次方（能将求模运算转为位运算提高效率），否则将影响效率
    private Integer bufferSize;


    @Bean
    public RingBuffer<BaseRequest> messageModelRingBuffer() {

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
                bufferSize,
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
