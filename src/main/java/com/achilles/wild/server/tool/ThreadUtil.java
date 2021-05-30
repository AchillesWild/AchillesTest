package com.achilles.wild.server.tool;

import java.util.concurrent.*;

public class ThreadUtil {

    public static ExecutorService getExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime){
        /*    任务缓存队列及排队策略
        synchronousQueue：直接提交。直接提交策略表示线程池不对任务进行缓存。新进任务直接提交给线程池，当线程池中没有空闲线程时，创建一个新的线程处理此任务。这种策略需要线程池具有无限增长的可能性。*/

       /*
        任务拒绝策略
        当线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize，如果还有任务到来就会采取任务拒绝策略
        ThreadPoolExecutor.CallerRunsPolicy;//由调用线程处理该任务*/
        ExecutorService executor = new ThreadPoolExecutor( corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
